package com.example.CF_Progress.APIInterfaces;

import com.example.CF_Progress.ProblemSetClasses.ProblemSet;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceProblemSet {
    /*
     * retrofit will use relative URL to fetch data and
     * it will add body to the following abstract method
     */
    @GET("problemset.problems")
    /*
     * we will get an "JSON object" and will store
     * the data of that object in our "ProblemSet" object.
     */
    Call<ProblemSet> getProblemSet();

}
