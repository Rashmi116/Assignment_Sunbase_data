package com.okayjava.html.model;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    @JsonProperty("access_token")
    private String access_token;

    // standard getters and setters
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    // Use the token as needed
    public String getStored_token(HttpSession session) {
    return (String) session.getAttribute("ACCESS_TOKEN");
    }
}
