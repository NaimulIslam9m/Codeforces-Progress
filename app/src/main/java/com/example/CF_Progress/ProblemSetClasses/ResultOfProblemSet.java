package com.example.CF_Progress.ProblemSetClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultOfProblemSet {
    @SerializedName("problems")
    @Expose
    List<Problems> problems;

    @SerializedName("problemStatistics")
    @Expose
    List<ProblemStatistics> problemStatistics;

    public List<Problems> getProblems() {
        return problems;
    }

    public List<ProblemStatistics> getProblemStatistics() {
        return problemStatistics;
    }
}
