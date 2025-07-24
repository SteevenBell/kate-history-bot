package com.katehistory.model.enums;

public enum Role {
    USER,
    ADMIN;

    public static Role safeValueOf(String s) {
        try {
            return valueOf(s.toUpperCase());
        } catch (Exception e) {
            return USER;
        }
    }
}
