package com.example.sub1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserStatus {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private List<Result> results = null;

    public String getStatus() {
        return status;
    }

    public List<Result> getResults() {
        return results;
    }
}
