package com.example.googlead;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Category {
    private String name;
    private String hours;
    private String score;
    private String country;
    private String badgeUrl;


    @SerializedName("body")
    private String text;

    public String getName() {
        return name;
    }
    public String getScore() {
        return score;
    }

    public String getHours() {
        return hours;
    }
    public String getCountry() {
        return country;
    }

    public String getImage() {
        return badgeUrl;
    }
}

