package com.example.sub1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Problem {
    @SerializedName("contestId")
    @Expose
    private int contestId;

    @SerializedName("index")
    @Expose
    private String index;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("points")
    @Expose
    private int points;

    @SerializedName("rating")
    @Expose
    private int rating;

    public int getContestId() {
        return contestId;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

    public int getRating() {
        return rating;
    }
}