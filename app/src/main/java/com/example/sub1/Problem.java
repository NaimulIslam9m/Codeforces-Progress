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

    /*
     * Same field is present on previous class
     * Integer.
     * Can be absent.
     * Id of the contest, in which party is participating.
     */
    public int getContestId() {
        return contestId;
    }

    /*
     * String.
     * Usually a letter of a letter, followed by a digit
     * Represent a problem index in a contest.
     */
    public String getIndex() {
        return index;
    }

    /*
     * Problem name
     */
    public String getName() {
        return name;
    }

    /*
     *
     */
    public String getType() {
        return type;
    }

    /*
     * Floating point number.
     * Can be absent.
     * Maximum ammount of points for the problem.
     */
    public int getPoints() {
        return points;
    }

    /*
     * Integer.
     * Can be absent.
     * Problem rating (difficulty).
     */
    public int getRating() {
        return rating;
    }
}