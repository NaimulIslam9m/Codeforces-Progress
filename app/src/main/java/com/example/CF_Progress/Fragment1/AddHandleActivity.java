package com.example.CF_Progress.Fragment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.CF_Progress.R;
import com.example.CF_Progress.SQLiteDataBase.DataBaseHelper;

public class AddHandleActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    EditText editTextHandle; // For taking input handle from the user
    Button addButton; // For submitting the input handle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_handle);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.25));

        dataBaseHelper = new DataBaseHelper(this);

        addButton = findViewById(R.id.addButtonId);
        editTextHandle = findViewById(R.id.addHandleEditTextId);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer text = new StringBuffer(editTextHandle.getText().toString());
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == ' ') {
                        text.deleteCharAt(i);
                        i--;
                    }
                }

                if (text.length() > 0) {
                    long rowId = dataBaseHelper.insertHandle(text.toString());
                    if (rowId != -1) {
                        toastMessage("Successfully added " + text);
                    } else {
                        toastMessage("Failed");
                    }
                } else {
                    toastMessage("Please input a handle");
                }
            }
        });
    }

    void toastMessage(String message) {
        Toast.makeText(AddHandleActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}