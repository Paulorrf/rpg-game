package com.evo.utils;

public class TokenValidationResult {
    private String email;
    private boolean isValid;

    public TokenValidationResult(String email, boolean isValid) {
        this.email = email;
        this.isValid = isValid;
    }

    public String getEmail() {
        return email;
    }

    public boolean isValid() {
        return isValid;
    }

    
}
