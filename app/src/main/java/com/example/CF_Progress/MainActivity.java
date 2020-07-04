package com.example.CF_Progress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextHandle; // For taking input handle from the user
    Button submitButton; // For submitting the input handle

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
        String text = editTextHandle.getText().toString();

        // if there is no handle in input then show a message with a toast
        if (!text.equals("")) {
            Intent intent = new Intent(MainActivity.this, DataActivity.class);
            intent.putExtra("tag", text);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Please input a handle", Toast.LENGTH_SHORT).show();
        }
    }
}