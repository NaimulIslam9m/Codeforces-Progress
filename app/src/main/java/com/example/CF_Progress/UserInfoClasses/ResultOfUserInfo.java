package com.example.CF_Progress.UserInfoClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultOfUserInfo {
    @SerializedName("handle")
    @Expose
    String handle;

    @SerializedName("firstName")
    @Expose
    String firstName;

    @SerializedName("lastName")
    @Expose
    String lastName;

    @SerializedName("avatar")
    @Expose
    String avatar;

    @SerializedName("rating")
    @Expose
    int rating;

    @SerializedName("maxRating")
    @Expose
    int maxRating;

    @SerializedName("rank")
    @Expose
    String rank;

    @SerializedName("maxRank")
    @Expose
    String maxRank;

    @SerializedName("lastOnlineTimeSeconds")
    @Expose
    long lastOnlineTimeSeconds;
    @SerializedName("country")
    @Expose
    String country;

    @SerializedName("organization")
    @Expose
    String organization;

    @SerializedName("contribution")
    @Expose
    int contribution;

    @SerializedName("registrationTimeSeconds")
    @Expose
    long registrationTimeSeconds;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("friendOfCount")
    @Expose
    int friendOfCount;

    public String getHandle() {
        return handle;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getRating() {
        return rating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public String getRank() {
        return rank;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public long getLastOnlineTimeSeconds() {
        return lastOnlineTimeSeconds;
    }

    public String getCountry() {
        return country;
    }

    public String getOrganization() {
        return organization;
    }

    public int getContribution() {
        return contribution;
    }

    public long getRegistrationTimeSeconds() {
        return registrationTimeSeconds;
    }

    public String getEmail() {
        return email;
    }

    public int getFriendOfCount() {
        return friendOfCount;
    }
}
