package com.beachape.auth;

import io.quarkus.security.identity.request.BaseAuthenticationRequest;

public class MyAuthRequest extends BaseAuthenticationRequest {

    private final String username;
    private final String password;

    public MyAuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
