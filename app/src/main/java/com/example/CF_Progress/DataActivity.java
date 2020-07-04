package com.example.CF_Progress;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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

    private static final String TAG = "DataActivity";
    private static final String BASE_URL = "https://codeforces.com/api/";
    private static final int COUNT = 1000; // Maximum number of submissions fetch from codeforces
    private static final int FROM = 1; // 1-based index of the first submission
    private Retrofit retrofit;
    private String handle;
    private TextView handleName;
    private ApiInterface apiInterface;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd");


    private Date x;
    private int y;

    private BubbleChart bubbleChart;
    private ArrayList bubbleEntries;
    private BubbleDataSet bubbleDataSet;
    private BubbleData bubbleData;


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

        bubbleChart = findViewById(R.id.graph);
        Log.d(TAG, "okK?");

        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        getStatus();
    }

    private void getStatus() {
        Log.d(TAG, "okk12");

        Call<UserStatus> call = apiInterface.getUserStatus(handle, FROM, COUNT);
        Log.d(TAG, "fucking shit!");
        call.enqueue(new Callback<UserStatus>() {

            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {
                Log.d(TAG, "okk3");
                try {
                    Log.d(TAG, "okk4");
                    List<Result> results = response.body().getResults();

                    // to get oldest to newest sumbission
                    Collections.reverse(results);


                    HashMap<Pair, Integer> hm = new HashMap<>();
                    for (Result result : results) {
                        if (result.getVerdict().equals("OK")) {
                            y = result.getProblem().getRating(); // rating
                            if (y < 800) continue;
                            x = new Date(result.getCreationTimeSeconds() * 1000L); // date
                            String vv = new SimpleDateFormat("MMM dd").format(x);
                            Pair<String, Integer> pair = new Pair(vv, y);
                            if (hm.get(pair) != null) {
                                hm.put(pair, hm.get(pair) + 1);
                                Log.d(TAG, "data loading is ok");
                            } else {
                                hm.put(pair, 1);
                            }
                        }
                    }


                    HashMap<String, Integer> cnt = new HashMap<>();
                    int freq = 1;
                    for (Pair i : hm.keySet()) {
                        String vv = (String)i.first;
                        if (cnt.get(vv) == null) {
                            cnt.put(vv, freq);
                        }
                            freq += hm.get(i);
                    }

                    bubbleEntries = new ArrayList<>();
                    bubbleEntries.add(new BubbleEntry(0f, 0f, 0f));
                    for (Pair i : hm.keySet()) {
                        String vv = (String)i.first;
                        y = (Integer)i.second;
                        bubbleEntries.add(new BubbleEntry((float)cnt.get(vv)*0.5f, (float)y, (float)hm.get(i)));
                    }

//                    bubbleEntries = new ArrayList<>();
//                    for (Pair i : hm.keySet()) {
//                        x = (Date) i.first;
//                        y = (Double) i.second;
//                        bubbleEntries.add(new BubbleEntry(x, y, hm.get(i)));
//                    }
                    bubbleDataSet = new BubbleDataSet(bubbleEntries, "Data set 1");
                    bubbleData = new BubbleData(bubbleDataSet);
                    bubbleChart.setData(bubbleData);


                    //axis
                    bubbleChart.getAxisRight().setEnabled(false);
                    XAxis xAxis = bubbleChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                    // attributes
                    bubbleDataSet.setValueTextColor(Color.BLACK);
                    bubbleDataSet.setValueTextSize(0f);
//                    bubbleDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                    bubbleDataSet.setColor(Color.RED);


                    // set scrollable and scalable
                    bubbleChart.setDragEnabled(true);
                    bubbleChart.setScaleEnabled(true);

                    Legend l = bubbleChart.getLegend();
                    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                    l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    l.setDrawInside(true);
                    l.setYOffset(20f);
                    l.setXOffset(0f);
                    l.setYEntrySpace(0f);
                    l.setTextSize(8f);

                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setDrawGridLines(false);
                    xAxis.setAxisMaximum(freq+1);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                    YAxis yAxis = bubbleChart.getAxisLeft();
                    yAxis.setDrawGridLines(false);
                    yAxis.setSpaceTop(35f);
                    yAxis.setAxisMinimum(750f);

                } catch (Exception e) {
                    Log.d(TAG, "Not Loading: " + e.getMessage());
                }
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