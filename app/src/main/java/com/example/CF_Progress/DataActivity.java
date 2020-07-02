package com.example.CF_Progress;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://codeforces.com/api/";
    private static final int COUNT = 10000; // number of submissions to get
    private static final int FROM = 1; // 1-based index of the first submission
    private Retrofit retrofit;
    private String handle;
    private TextView handleName;
    private ApiInterface apiInterface;


    //    for graph
    private PointsGraphSeries<DataPoint> scatterPlot;
    private GraphView graph;
    private int x, y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

//        for passing data from one layout to another
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            handle = bundle.getString("tag");
        }

        handleName = findViewById(R.id.handleNameId);
        handleName.setText(handle);

        // for graph
        graph = findViewById(R.id.graph);
        scatterPlot = new PointsGraphSeries<>();

        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        getStatus();
    }

    private void getStatus() {
        Call<UserStatus> call = apiInterface.getUserStatus(handle, FROM, COUNT);

        call.enqueue(new Callback<UserStatus>() {
            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {

                List<Result> results = response.body().getResults();

//                to get oldest sumbission first
                Collections.reverse(results);

                x = 1;
                int count = 0;
                for (Result result : results) {
                    if (result.getVerdict().equals("OK")) count++;
                }

                for (Result result : results) {
                    if (result.getVerdict().equals("OK")) {
                        y = result.getProblem().getRating();
                        if (y < 800) continue;
                        scatterPlot.appendData(new DataPoint(x, y), true, count);
                        x++;
                    }
                }

                //        set some properties
                scatterPlot.setShape(PointsGraphSeries.Shape.POINT);
                scatterPlot.setColor(Color.BLACK);
                scatterPlot.setSize(5f);

                // set scrollable and scalable
                graph.getViewport().setScalable(true);
                graph.getViewport().setScalableY(true);
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScrollableY(true);

                // set manual y bounds
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMaxY(count + 10);
                graph.getViewport().setMinY(500);

                // set manual x bounds
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMaxX(count + 10);
                graph.getViewport().setMinX(0);

                graph.addSeries(scatterPlot);

                graph.addSeries(scatterPlot);
                /*
                 * custom lable
                 */
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show normal x values
                            return super.formatLabel(value, isValueX);
                        } else {
                            // show currency of y values
                            return "." + super.formatLabel(value, isValueX);
                        }
                    }
                });
                /*
                 * Tap listener on data points
                 */
                scatterPlot.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        String message = "X: " + dataPoint.getX() + "\nY: " + dataPoint.getY();
                        Toast.makeText(DataActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }
}