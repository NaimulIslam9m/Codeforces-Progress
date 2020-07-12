package com.example.CF_Progress.UserStatusClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("participantType")
    @Expose
    String participantType;

    public String getParticipantType() {
        return participantType;
    }
}
