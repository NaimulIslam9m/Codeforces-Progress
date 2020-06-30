package com.example.sub1;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("user.status")
    Call<UserStatus> getUserStatus(@Query("handle") String handle,
                                   @Query("from") int from,
                                   @Query("count") int count
    );

}
