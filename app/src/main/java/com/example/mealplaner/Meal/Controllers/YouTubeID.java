package com.example.mealplaner.Meal.Controllers;

public class YouTubeID {
    private String vedioID;
    private String vedioUrl;

    public YouTubeID(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public void setVedioLink(String vedioLink) {
        this.vedioUrl = vedioUrl;
    }

    public String getVedioID() {
        String [] vedio=vedioUrl.split("=");
        vedioID = vedio[1];

        return vedioID;
    }
}
