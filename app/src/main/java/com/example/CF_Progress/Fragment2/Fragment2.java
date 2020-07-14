package com.example.CF_Progress.Fragment2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.CF_Progress.APIInterfaces.ApiInterfaceProblemSet;
import com.example.CF_Progress.ProblemSetClasses.ProblemSet;
import com.example.CF_Progress.ProblemSetClasses.Problems;
import com.example.CF_Progress.R;

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

    RecyclerView recyclerView;
    ProblemListAdapter problemListAdapter;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = "MainActivityFF";
    private static final String BASE_URL = "https://codeforces.com/api/";

    private Retrofit retrofit;
    private ApiInterfaceProblemSet apiInterfacePS;

    List<String> problemNames = new ArrayList<>();
    HashMap<String, String> problemUrl = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        searchView = view.findViewById(R.id.searchViewId);
        recyclerView = view.findViewById(R.id.recyclerViewProblemListId);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutId);

        retrofitFun();
        getProblemList();

        problemListAdapter = new ProblemListAdapter(getContext(), problemNames);
        recyclerView.setAdapter(problemListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                problemNames.clear();
                getProblemList();
                problemListAdapter = new ProblemListAdapter(getActivity(), problemNames);
                recyclerView.setAdapter(problemListAdapter);
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

        problemListAdapter.setOnItemClickListener(new ProblemListAdapter.ClickListener() {
            @Override
            public void OnItemClick(int position, View v) {
                Intent intentProblemActivity = new Intent(v.getContext(), ProblemActivity.class);
                intentProblemActivity.putExtra("tag", problemUrl.get(problemNames.get(position)));
                startActivity(intentProblemActivity);
            }
        });


/*        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/

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
        Log.d("Fuck", "1");
        Call<ProblemSet> call = apiInterfacePS.getProblemSet();
        Log.d("Fuck", "1");
        call.enqueue(new Callback<ProblemSet>() {

            @Override
            public void onResponse(Call<ProblemSet> call, Response<ProblemSet> response) {
                Log.d("Fuck", "1");
                List<Problems> results = response.body().getResults().getProblems();
                Log.d("Fuck", "1");
                for (Problems result : results) {
                    String name = result.getContestId() + result.getIndex() + ": " + result.getName();
                    String url = "https://codeforces.com/problemset/problem/" + result.getContestId() + "/" + result.getIndex() + "?mobile=true";
                    problemNames.add(name);
                    problemUrl.put(name, url);
                    problemListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProblemSet> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}