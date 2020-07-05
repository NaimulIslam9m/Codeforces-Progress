package com.example.CF_Progress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 2 mothods in DataActivity class
 * - onCreate
 * - getStatus
 */
public class DataActivity extends AppCompatActivity {

    private static final String TAG = "DataActivityFF";
    private static final String BASE_URL = "https://codeforces.com/api/";
    private static final int COUNT = 10000; // Maximum number of submissions fetch from codeforces
    private static final int FROM = 1; // 1-based index of the first submission

    private Retrofit retrofit;
    private String handle;
    private TextView handleName;
    private ApiInterface apiInterface;

    private int x;
    private int y;

    private ScatterChart scatterChart;
    private ArrayList<Entry> entries = new ArrayList<>();
    private ScatterDataSet scatterDataSet;
    private ScatterData scatterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        // for passing data from one layout to another
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            handle = bundle.getString("tag");
        }
        handleName = findViewById(R.id.handleNameId);
        handleName.setText(handle);

        scatterChart = findViewById(R.id.scatterChart);

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

                // to get oldest to newest sumbission
                Collections.reverse(results);

                x = 0;
                for (Result result : results) {
                    if (result.getVerdict().equals("OK")) {
                        y = result.getProblem().getRating(); // rating
                        if (y < 800) continue;
                        entries.add(new Entry(x, y));
                        x++;
                    }
                }



                scatterDataSet = new ScatterDataSet(entries, "data 1");
                scatterDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                scatterData = new ScatterData(scatterDataSet);
                scatterChart.setData(scatterData);


                //axis
                scatterChart.getAxisRight().setEnabled(false);
                XAxis xAxis = scatterChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



                // set scrollable and scalable
                scatterChart.setDragEnabled(true);
                scatterChart.setScaleEnabled(true);

                scatterChart.animateXY(0, 5000, Easing.EaseOutBounce, Easing.EaseOutBounce);
                scatterChart.invalidate();


                Legend l = scatterChart.getLegend();
                l.setEnabled(true);
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(true);
                l.setYOffset(20f);
                l.setXOffset(0f);
                l.setYEntrySpace(0f);
                l.setTextSize(8f);
//                l.setTextColor();
//                l.setForm(l.LegendForm.SQUARE);
//                l.setFormSize();
//                l.setXEntrySpace();
//                l.setFormToTextSpace();

                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setDrawGridLines(true);
                xAxis.setAxisMaximum(x+1);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                YAxis yAxis = scatterChart.getAxisLeft();
                yAxis.setDrawGridLines(true);
                yAxis.setSpaceTop(35f);
                yAxis.setAxisMinimum(750f);
            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}