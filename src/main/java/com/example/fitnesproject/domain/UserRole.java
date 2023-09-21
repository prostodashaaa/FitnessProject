package com.example.fitnesproject.domain;

public enum UserRole {
    USER("USER"), ADMIN("ADMIN");

    private final String dbString;

    private UserRole(String dbString){
        this.dbString = dbString;
    }

    public String getDbString() {
        return dbString;
    }
}
