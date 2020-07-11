package com.example.CF_Progress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment2 extends Fragment {

    public Fragment2() {
        // Required empty public constructor
    }

    ListView listView;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = "MainActivityFF";
    private static final String BASE_URL = "https://codeforces.com/api/";

    private Retrofit retrofit;
    private ApiInterfaceProblemSet apiInterfacePS;

    List<String> problemNames = new ArrayList<>();
    HashMap<String, String> problemUrl = new HashMap<>();

    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        searchView = view.findViewById(R.id.searchViewId);
        listView = view.findViewById(R.id.listViewId);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutId);

        retrofitFun();
        getProblemList();

        adapter = new ArrayAdapter<>(getActivity(), R.layout.sample_view, R.id.textViewId, problemNames);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProblemList();
                adapter = new ArrayAdapter<>(getActivity(), R.layout.sample_view, R.id.textViewId, problemNames);
                listView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentProblemActivity = new Intent(view.getContext(), ProblemActivity.class);
                intentProblemActivity.putExtra("tag", problemUrl.get(adapter.getItem(position)));
                startActivity(intentProblemActivity);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }

    private void retrofitFun() {
        // API client
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterfacePS = retrofit.create(ApiInterfaceProblemSet.class);
    }

    private void getProblemList() {
        Call<ProblemSet> call = apiInterfacePS.getProblemSet();
        call.enqueue(new Callback<ProblemSet>() {

            @Override
            public void onResponse(Call<ProblemSet> call, Response<ProblemSet> response) {
                List<Problems> results = response.body().getResults().getProblems();
                for (Problems result : results) {
                    String name = result.getContestId() + result.getIndex() + ": " + result.getName();
                    String url = "https://codeforces.com/problemset/problem/" + result.getContestId() + "/" + result.getIndex() + "?mobile=true";
                    problemNames.add(name);
                    problemUrl.put(name, url);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProblemSet> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}