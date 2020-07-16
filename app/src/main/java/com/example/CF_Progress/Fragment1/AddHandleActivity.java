package com.example.CF_Progress.Fragment1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.CF_Progress.APIInterfaces.ApiInterfaceGetUserInfo;
import com.example.CF_Progress.R;
import com.example.CF_Progress.SQLiteDataBase.DataBaseHelper;
import com.example.CF_Progress.UserInfoClasses.ResultOfUserInfo;
import com.example.CF_Progress.UserInfoClasses.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddHandleActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    EditText editTextHandle; // For taking input handle from the user
    Button addButton; // For submitting the input handle

    private Retrofit retrofit;
    private ApiInterfaceGetUserInfo apiInterfaceGUI;
    private static final String BASE_URL = "https://codeforces.com/api/";
    String imageUrl, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_handle);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.25));

        retrofitFun();

        dataBaseHelper = new DataBaseHelper(this);

        addButton = findViewById(R.id.addButtonId);
        editTextHandle = findViewById(R.id.addHandleEditTextId);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editTextHandle.getText().toString();
                editTextHandle.getText().clear();
                text = text.replaceAll(" ", "");

                if (text.length() > 0) {

                    Call<UserInfo> callUI = apiInterfaceGUI.getUserInfo(text);
                    callUI.enqueue(new Callback<UserInfo>() {

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                            List<ResultOfUserInfo> results = response.body().getResultOfUserInfo();
                            ResultOfUserInfo result = results.get(0);
                            imageUrl = "https:" + result.getAvatar();

                            long rowId = dataBaseHelper.insertHandle(text, imageUrl);
                            if (rowId != -1) {
                                toastMessage("Successfully added " + text);

                            } else {
                                toastMessage("Failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<UserInfo> call, Throwable t) {

                        }
                    });


                } else {
                    toastMessage("Please input a handle");
                }
            }
        });
    }

    private void retrofitFun() {
        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterfaceGUI = retrofit.create(ApiInterfaceGetUserInfo.class);
    }

    void toastMessage(String message) {
        Toast.makeText(AddHandleActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}