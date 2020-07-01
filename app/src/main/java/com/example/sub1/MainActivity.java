package com.example.sub1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    For taking input handle from the user
    EditText editTextHandle;

    //    For submitting the input handle
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = findViewById(R.id.submitButtonId);
        editTextHandle = findViewById(R.id.handleId);

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DataActivity.class);
        intent.putExtra("tag", editTextHandle.getText().toString());
        startActivity(intent);
    }
}