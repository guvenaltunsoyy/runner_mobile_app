package com.example.runner_mobile_app;

import android.media.Image;

public class Runner {
    private String username;
    private int runcount;
    private String image;
    private String title;


    public Runner(String username,int runcount, String imgage,String title) {
        this.username = username;
        this.runcount = runcount;
        this.image = imgage;
        this.title=title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRuncount() {
        return runcount;
    }

    public void setRuncount(int runcount) {
        this.runcount = runcount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
