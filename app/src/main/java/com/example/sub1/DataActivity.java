package com.example.sub1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
    private LineGraphSeries<DataPoint> plot1;
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
        plot1 = new LineGraphSeries<>();

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
                        plot1.appendData(new DataPoint(x, y), true, count);
                        x++;
                    }
                }
                graph.addSeries(plot1);
            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }
}