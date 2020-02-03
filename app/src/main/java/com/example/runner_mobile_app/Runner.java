package com.example.runner_mobile_app;

import android.media.Image;

public class Runner {
    private String username;
    private int runcount;
    private String image;
    private String title;
    private String name;
    private String phonenumber;
    private String mail;
    private int id;
    private boolean state;
    private int age;

    public Runner(String username, int runcount, String image, String title) {
        this.username = username;
        this.runcount = runcount;
        this.image = image;
        this.title = title;
    }

    public Runner(String username, int runcount, String image, String title, String name, String phonenumber, String mail, int id, boolean state, int age) {
        this.username = username;
        this.runcount = runcount;
        this.image = image;
        this.title = title;
        this.name = name;
        this.phonenumber = phonenumber;
        this.mail = mail;
        this.id = id;
        this.state = state;
        this.age = age;
    }
    public Runner(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
