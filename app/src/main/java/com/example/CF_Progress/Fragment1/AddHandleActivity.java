package com.example.CF_Progress.Fragment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.CF_Progress.R;

public class AddHandleActivity extends AppCompatActivity {

    EditText editTextHandle; // For taking input handle from the user
    Button submitButton; // For submitting the input handle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_handle);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.25));

        submitButton = findViewById(R.id.submitButtonId);
        editTextHandle = findViewById(R.id.handleId);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextHandle.getText().toString();

                // if there is no handle in input then show a message with a toast
                if (!text.equals("")) {
                    Intent intent = new Intent(AddHandleActivity.this, DataActivity.class);
                    intent.putExtra("tag", text);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddHandleActivity.this, "Please input a handle", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}