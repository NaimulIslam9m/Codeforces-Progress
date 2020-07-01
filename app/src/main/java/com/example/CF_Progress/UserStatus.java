package com.example.CF_Progress;

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

    /*
     * status is either "OK" or "FAILED".
     */
    public String getStatus() {
        return status;
    }

    /*
     * Returns a "list of Submission objects", sorted in decreasing order of submission id.
     */
    public List<Result> getResults() {
        return results;
    }
}
