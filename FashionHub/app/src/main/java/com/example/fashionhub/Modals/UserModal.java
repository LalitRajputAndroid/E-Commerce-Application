package com.example.fashionhub.Modals;

public class UserModal {
    private String name,email,password,userID;

    public UserModal() {
    }

    public UserModal(String name, String email, String password, String userID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = userID;
    }

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
