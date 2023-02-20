package com.example.mealplaner.OnBoardind.View;

public class OnBoardinfIteam {
    private String tvDiscript;
    private String lotteName;

    public OnBoardinfIteam(String tvDiscript, String lotteName) {
        this.tvDiscript = tvDiscript;
        this.lotteName = lotteName;
    }

    public String getLotteName() {
        return lotteName;
    }

    public String getTvDiscript() {
        return tvDiscript;
    }

    public void setTvDiscript(String tvDiscript) {
        this.tvDiscript = tvDiscript;
    }
}
