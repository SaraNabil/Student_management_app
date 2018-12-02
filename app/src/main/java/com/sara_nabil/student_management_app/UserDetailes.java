package com.sara_nabil.student_management_app;

public class UserDetailes {
    private String name ;
    private String email ;
    private int phone ;

    public UserDetailes(String name, String email, int phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }
}
