package com.example.inventario;

public class User {
    public String userId;
    public String displayName;
    public String email;
    public String cumple;

    public User(String userId, String displayName, String email, String cumple) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.cumple = cumple;
    }

    public User(){}

}