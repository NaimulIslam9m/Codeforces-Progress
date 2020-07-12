package com.example.CF_Progress.UserStatusClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserStatus {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private List<ResultUS> results = null;

    @SerializedName("comment")
    @Expose
    private String comment = "";

    /*
     * status is either "OK" or "FAILED".
     */
    public String getStatus() {
        return status;
    }

    /*
     * Returns a "list of Submission objects", sorted in decreasing order of submission id.
     */
    public List<ResultUS> getResults() {
        return results;
    }

    /*
     *If status is "FAILED" then comment contains the reason why the request failed.
     * If status is "OK", then there is no comment.
     */
    public String getComment() {
        return comment;
    }
}
