package com.example.groupassignment.DAO;

//author of this class : jiayu jian

public class Customer {
    private String id;
    private String username;
    private String password;

    public Customer(String id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString()
    {
        return "id: " + this.id + " - userName: " + this.username + " - password: " + this.password;
    }
}
