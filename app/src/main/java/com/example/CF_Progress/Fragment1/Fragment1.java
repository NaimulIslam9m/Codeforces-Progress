package com.example.CF_Progress.Fragment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.CF_Progress.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }

    FloatingActionButton addHandleButton, removeHandleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        addHandleButton = view.findViewById(R.id.addHandleButtonId);
        removeHandleButton = view.findViewById(R.id.removeHandleButtonId);

        addHandleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddHandleActivity.class));
            }
        });

        removeHandleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Nothing to do!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}