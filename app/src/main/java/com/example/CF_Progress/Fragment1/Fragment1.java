package com.example.CF_Progress.Fragment1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.CF_Progress.R;
import com.example.CF_Progress.SQLiteDataBase.DataBaseHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    public Fragment1() {
        // Required empty public constructor
    }

    SwipeRefreshLayout swipeRefreshLayout;
    List<String> handleNames = new ArrayList<>();
    List<String> handleImages = new ArrayList<>();
    DataBaseHelper dataBaseHelper;
    FloatingActionButton addHandleButton, removeHandleButton;
    HandleListAdapter handleListAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        dataBaseHelper = new DataBaseHelper(getContext());

        addHandleButton = view.findViewById(R.id.addHandleButtonId);
        removeHandleButton = view.findViewById(R.id.removeHandleButtonId);
        recyclerView = view.findViewById(R.id.recyclerViewHandleId);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutId);

        showHandles();

        handleListAdapter = new HandleListAdapter(getContext(), handleNames, handleImages);
        recyclerView.setAdapter(handleListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handleNames.clear();
                handleImages.clear();

                showHandles();

                handleListAdapter = new HandleListAdapter(getActivity(), handleNames, handleImages);
                recyclerView.setAdapter(handleListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LayoutAnimationController layoutAnimationController =
                        AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_fall_down);

                recyclerView.setLayoutAnimation(layoutAnimationController);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();

                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        handleListAdapter.setOnItemClickListener(new HandleListAdapter.ClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                Intent intent = new Intent(v.getContext(), DataActivity.class);
                intent.putExtra("tag", handleNames.get(position));
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
                handleImages.add(resultSet.getString(1));
            }
        } else {
            toastMessage("No data found");
        }
    }

    void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}