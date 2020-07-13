package com.example.CF_Progress.Fragment1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.CF_Progress.R;
import com.example.CF_Progress.SQLiteDataBase.DataBaseHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }

    ListView listView;
    List<String> handleNames = new ArrayList<>();
    DataBaseHelper dataBaseHelper;
    FloatingActionButton addHandleButton, removeHandleButton;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        dataBaseHelper = new DataBaseHelper(getContext());

        addHandleButton = view.findViewById(R.id.addHandleButtonId);
        removeHandleButton = view.findViewById(R.id.removeHandleButtonId);
        listView = view.findViewById(R.id.listViewHandleId);
        showHandles();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.sample_view, R.id.textViewId, handleNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DataActivity.class);
                intent.putExtra("tag", adapter.getItem(position));
                startActivity(intent);
            }
        });

        addHandleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddHandleActivity.class));
            }
        });

        removeHandleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DeleteHandleActivity.class));
            }
        });

        return view;
    }

    public void showHandles() {
        Cursor resultSet = dataBaseHelper.displayAllHandles();
        if (resultSet.getCount() != 0) {
            while (resultSet.moveToNext()) {
                handleNames.add(resultSet.getString(0));
            }
        } else {
            toastMessage("no data found");
        }
    }

    void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}