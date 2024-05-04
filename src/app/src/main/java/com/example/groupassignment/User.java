package com.example.groupassignment;

public class User {

    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    public void watchVideo() {
        // 在这里添加观看视频的具体逻辑
        System.out.println(name + " is watching a video.");
    }


    public void discuss() {
        // 在这里添加讨论的具体逻辑
        System.out.println(name + " is discussing.");
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}