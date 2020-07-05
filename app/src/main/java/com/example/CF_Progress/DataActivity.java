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
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

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

    private int x = 0;
    private int y;



    private ScatterChart scatterChart;
    private ArrayList<ArrayList<Entry>> entries = new ArrayList<>(27);
    private ArrayList<IScatterDataSet> scatterDataSets = new ArrayList<>();
    private ScatterData scatterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
Log.d(TAG, "1");

        // for passing data from one layout to another
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            handle = bundle.getString("tag");
        }
Log.d(TAG, "2");
        handleName = findViewById(R.id.handleNameId);
        handleName.setText(handle);
Log.d(TAG, "3");
        scatterChart = findViewById(R.id.scatterChart);
Log.d(TAG, "4");
        retrofitFun();
        init();
        Log.d(TAG, "HOw are you?");
        getStatus();
    }

    private void retrofitFun() {
        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void init() {
        for (int i = 0; i < 27; i++) {
            entries.add(new ArrayList());
        }
    }

    private void getStatus() {
        Call<UserStatus> call = apiInterface.getUserStatus(handle, FROM, COUNT);
        Log.d(TAG, "Hello");
        call.enqueue(new Callback<UserStatus>() {

            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {

                List<Result> results = response.body().getResults();

                // to get oldest to newest sumbission
                Collections.reverse(results);

                for (Result result : results) {
                    if (result.getVerdict().equals("OK")) {
                        y = result.getProblem().getRating(); // rating
                        if (y < 800) continue;
                        entries.get(y/100 - 8).add(new Entry(x, y));
                        x++;
                    }
                }


//                scatterDataSet = new ScatterDataSet(entries.get(0), "data 1");
//                scatterDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//                scatterData = new ScatterData(scatterDataSet);
//                scatterChart.setData(scatterData);

                int[] ratingArray = getResources().getIntArray(R.array.cf_lvl);

                Log.d(TAG, "Hi");
                for (int i = 0; i < 27; i++) {
                    if (entries.get(i).size() > 0) {
                        ScatterDataSet sds = new ScatterDataSet(entries.get(i), "data " + ((i + 8) * 100));
                        sds.setColor(ratingArray[i]);
                        scatterDataSets.add(sds);
                    }
                }
                scatterData = new ScatterData(scatterDataSets);
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
                setLegendVal(l, -30, 10);


                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setDrawGridLines(true);
                xAxis.setAxisMaximum(x + 1);
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

    private void setLegendVal(Legend l, float x, float y) {
        l.setEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        l.setDrawInside(true);
        l.setYOffset(y);
        l.setXOffset(x);

        l.setXEntrySpace(-30f);
        l.setTextSize(4f);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(4f);
        l.setXEntrySpace(-10);
        l.setFormToTextSpace(1);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}