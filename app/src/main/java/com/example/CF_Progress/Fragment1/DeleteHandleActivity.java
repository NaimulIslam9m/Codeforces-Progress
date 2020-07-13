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

public class DeleteHandleActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    EditText editTextHandle; // For taking input handle from the user
    Button deleteButton; // For deleting the input handle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_handle);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.25));

        dataBaseHelper = new DataBaseHelper(this);

        deleteButton = findViewById(R.id.deleteButtonId);
        editTextHandle = findViewById(R.id.deleteHandleEditTextId);

        deleteButton.setOnClickListener(new View.OnClickListener() {
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
                    int val = dataBaseHelper.deleteHandle(text.toString());
                    if (val > 0) {
                        toastMessage("deleted");
                    } else {
                        toastMessage("error");
                    }
                } else {
                    Toast.makeText(DeleteHandleActivity.this, "Please input a handle", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void toastMessage(String message) {
        Toast.makeText(DeleteHandleActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}