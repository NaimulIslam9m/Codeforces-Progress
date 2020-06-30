package com.example.sub1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("contestId")
    @Expose
    private long contestId;

    @SerializedName("creationTimeSeconds")
    @Expose
    private long creationTimeSeconds;

    @SerializedName("relativeTimeSeconds")
    @Expose
    private long relativeTimeSeconds;

    @SerializedName("problem")
    @Expose
    private Problem problem;

    @SerializedName("programmingLanguage")
    @Expose
    private String programmingLanguage;

    @SerializedName("verdict")
    @Expose
    private String verdict;

    @SerializedName("testset")
    @Expose
    private String testset;

    @SerializedName("passedTestCount")
    @Expose
    private int passedTestCount;

    @SerializedName("timeConsumedMillis")
    @Expose
    private int timeConsumedMillis;

    @SerializedName("memoryConsumedBytes")
    @Expose
    private int memoryConsumedBytes;


    public long getId() {
        return id;
    }

    public long getContestId() {
        return contestId;
    }

    public long getCreationTimeSeconds() {
        return creationTimeSeconds;
    }

    public long getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    public Problem getProblem() {
        return problem;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public String getVerdict() {
        return verdict;
    }

    public String getTestset() {
        return testset;
    }

    public int getPassedTestCount() {
        return passedTestCount;
    }

    public int getTimeConsumedMillis() {
        return timeConsumedMillis;
    }

    public int getMemoryConsumedBytes() {
        return memoryConsumedBytes;
    }
}
