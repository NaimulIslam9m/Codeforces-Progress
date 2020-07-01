package com.example.sub1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

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
    public static final String BASE_URL = "https://codeforces.com/api/";
    private Retrofit retrofit;
    private String handle;
    private TextView handleName;
    private com.example.sub1.ApiInterface apiInterface;

    // for graph
    private LineGraphSeries<DataPoint> series1;
    private int x, y;
    private GraphView graph;
    //for graph

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            handle = bundle.getString("tag");
        }

        handleName = findViewById(R.id.handleNameId);
        handleName.setText(handle);

        // for graph
        graph = findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();
        // for graph

        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(com.example.sub1.ApiInterface.class);

        getStatus();
    }

    private void getStatus() {
        Call<com.example.sub1.UserStatus> call = apiInterface.getUserStatus(handle, 1, 10000);

        call.enqueue(new Callback<com.example.sub1.UserStatus>() {
            @Override
            public void onResponse(Call<com.example.sub1.UserStatus> call, Response<com.example.sub1.UserStatus> response) {

                List<Result> results = response.body().getResults();
                Collections.reverse(results);
                x = 1;

                int count = 0;
                for (Result result : results) {
                    if (result.getVerdict().equals("OK")) count++;
                }

                for (com.example.sub1.Result result : results) {
                    if (result.getVerdict().equals("OK")) {
                        y = result.getProblem().getRating();
                        series1.appendData(new DataPoint(x, y), true, count);
                        x++;
                    }
                }
                graph.addSeries(series1);
            }

            @Override
            public void onFailure(Call<com.example.sub1.UserStatus> call, Throwable t) {
                //textView.setText(t.getMessage());
            }
        });
    }
}