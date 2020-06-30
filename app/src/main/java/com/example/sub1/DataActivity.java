package com.example.sub1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

    private TextView textView;
    private com.example.sub1.ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            handle = bundle.getString("tag");
        }

        textView = findViewById(R.id.textViewId);

        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(com.example.sub1.ApiInterface.class);

        getStatus();
    }

    private void getStatus() {
        Call<com.example.sub1.UserStatus> call = apiInterface.getUserStatus(handle, 1, 100);

        call.enqueue(new Callback<com.example.sub1.UserStatus>() {
            @Override
            public void onResponse(Call<com.example.sub1.UserStatus> call, Response<com.example.sub1.UserStatus> response) {
                if(!response.isSuccessful()) {
                    textView.setText("code: " + response.code());
                    return;
                }
                List<com.example.sub1.Result> results = response.body().getResults();

                for (com.example.sub1.Result result : results) {
                    String content = "";
                    content += "Name: " + result.getProblem().getName() + "\n";
                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<com.example.sub1.UserStatus> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}