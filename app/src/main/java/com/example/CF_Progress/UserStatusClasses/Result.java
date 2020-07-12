package com.example.CF_Progress.UserStatusClasses;

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

    /*
     *
     */
    public long getId() {
        return id;
    }

    /*
     * Integer.
     * Can be absent.
     * Id of the contest, in which party is participating.
     */
    public long getContestId() {
        return contestId;
    }

    /*
     *
     */
    public long getCreationTimeSeconds() {
        return creationTimeSeconds;
    }

    /*
     * Integer.
     * Can be absent.
     * Number of seconds, passed after the start of the contest.
     * Can be negative.
     */
    public long getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    /*
     * Returns an object which has details of a problem
     */
    public Problem getProblem() {
        return problem;
    }

    /*
     * Language used to submit a program
     */
    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    /*
     * Verdict of the submission
     */
    public String getVerdict() {
        return verdict;
    }

    /*
     *
     */
    public String getTestset() {
        return testset;
    }

    /*
     * Number of test case has passed
     */
    public int getPassedTestCount() {
        return passedTestCount;
    }

    /*
     * Runtime of the program
     */
    public int getTimeConsumedMillis() {
        return timeConsumedMillis;
    }

    /*
     * Allocated memory of the program
     */
    public int getMemoryConsumedBytes() {
        return memoryConsumedBytes;
    }
}
