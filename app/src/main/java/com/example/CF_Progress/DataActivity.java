package com.example.CF_Progress;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.ScatterChart;
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
    private ApiInterfaceGetStatus apiInterfaceGS;

    private int x = 0;
    private int y;

    private ScatterChart scatterChart;
    private ArrayList<ArrayList<Entry>> entries = new ArrayList<>();
    private ArrayList<IScatterDataSet> scatterDataSets = new ArrayList<>();
    private ScatterData scatterData;

    private LinearLayout LLProgressBar, LLData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        LLProgressBar = findViewById(R.id.LLProgressBarId);
        LLData = findViewById(R.id.LLDataId);

        // for passing data from one layout to another
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            handle = bundle.getString("tag");
        }
        handleName = findViewById(R.id.handleNameId);
        handleName.setText(handle);
        scatterChart = findViewById(R.id.scatterChart);

        retrofitFun();
        init();
        new Task().execute();
    }

    class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            LLProgressBar.setVisibility(View.VISIBLE);
            LLData.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Call<UserStatus> call = apiInterfaceGS.getUserStatus(handle, FROM, COUNT);
            call.enqueue(new Callback<UserStatus>() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {

                    List<Result> results = response.body().getResults();

                    // to get oldest to newest sumbission
                    Collections.reverse(results);

                    for (Result result : results) {
                        if (result.getVerdict().equals("OK")) {
                            y = result.getProblem().getRating(); // rating
                            if (y >= 800 && y <= 3500) {
                                entries.get(y / 100 - 8).add(new Entry(x, y));
                                x++;
                            }
                        }
                    }

                    int[] ratingArray = getResources().getIntArray(R.array.cf_lvl);

                    for (int i = 0; i < 27; i++) {
                        if (entries.get(i).size() > 0) {
                            ScatterDataSet sds = new ScatterDataSet(entries.get(i), "");
                            sds.setColor(ratingArray[i]);
                            sds.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
                            sds.setScatterShapeSize(12f);
                            scatterDataSets.add(sds);
                        }
                    }

                    scatterData = new ScatterData(scatterDataSets);
                    scatterChart.setData(scatterData);

                    // set scrollable and scalable
                    scatterChart.setDragEnabled(true);
                    scatterChart.setScaleEnabled(true);

                    scatterChart.animateXY(0, 5000, Easing.EaseOutBounce, Easing.EaseOutBounce);
                    scatterChart.invalidate();

                    scatterChart.getDescription().setEnabled(false);
                    scatterChart.getLegend().setEnabled(false);

                    axisProperties();
                }

                @Override
                public void onFailure(Call<UserStatus> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });

            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            LLProgressBar.setVisibility(View.GONE);
            LLData.setVisibility(View.VISIBLE);
            super.onPostExecute(aBoolean);
        }
    }

    private void retrofitFun() {
        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterfaceGS = retrofit.create(ApiInterfaceGetStatus.class);
    }

    private void init() {
        for (int i = 0; i <= 27; i++) {
            entries.add(new ArrayList());
        }
    }

    private void axisProperties() {
        scatterChart.getAxisRight().setEnabled(false);
        XAxis xAxis = scatterChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMaximum(x + 10);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = scatterChart.getAxisLeft();
        yAxis.setDrawGridLines(true);
        yAxis.setSpaceTop(0);
        yAxis.setAxisMinimum(750f);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}