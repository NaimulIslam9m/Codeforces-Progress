package com.example.CF_Progress.APIInterfaces;

import com.example.CF_Progress.UserInfoClasses.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceGetUserInfo {
    /*
     * retrofit will use relative URL to fetch data and
     * it will add body to the following abstract method
     */
    @GET("user.info")
    /*
     * we will get an "JSON object" and will store
     * the data of that object in our "UserStatus" object.
     *
     * the parameters of getUserInfo function:
     * handle: Codeforces user handle.
     *
     */
    Call<UserInfo> getUserInfo(@Query("handles") String handle);
}
