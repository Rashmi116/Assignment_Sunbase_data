package com.okayjava.html.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentials {
    @JsonProperty("login_id")
    private String loginId;
    @JsonProperty("password")
    private String password;
}