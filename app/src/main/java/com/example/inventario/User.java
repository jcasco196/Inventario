package com.example.inventario;

import android.widget.EditText;

public class User {
    public String userId;
    public String displayName;
    public String email;

    public User(String userId, String displayName, String email) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
    }

    public User(){}

}