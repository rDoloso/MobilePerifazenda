package com.example.mobileperifazenda.models;

public class LoginRequest {
    private String username;
    private String senha;

    public LoginRequest(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }
}

