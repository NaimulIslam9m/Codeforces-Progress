package com.example.CF_Progress;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    /*
     * retrofit will use relative URL to fetch data and
     * it will add body to the following abstract method
     */
    @GET("user.status")
    /*
     * we will get an "JSON object" and will store
     * the data of that object in our "UserStatus" object.
     *
     * the parameters of getUserStatus function:
     * handle: Codeforces user handle.
     * from: 1-based index of the first submission to return.
     * count: Number of returned submissions.
     *
     */
    Call<UserStatus> getUserStatus(@Query("handle") String handle,
                                   @Query("from") int from,
                                   @Query("count") int count
    );

}
