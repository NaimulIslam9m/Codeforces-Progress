package com.example.CF_Progress;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }

    EditText editTextHandle; // For taking input handle from the user
    Button submitButton; // For submitting the input handle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        submitButton = view.findViewById(R.id.submitButtonId);
        editTextHandle = view.findViewById(R.id.handleId);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextHandle.getText().toString();

                // if there is no handle in input then show a message with a toast
                if (!text.equals("")) {
                    Intent intent = new Intent(getActivity(), DataActivity.class);
                    intent.putExtra("tag", text);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Please input a handle", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}