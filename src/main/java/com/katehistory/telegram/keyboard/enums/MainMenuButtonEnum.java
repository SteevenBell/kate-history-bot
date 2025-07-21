package com.katehistory.telegram.keyboard.enums;

import lombok.Getter;

@Getter
public enum MainMenuButtonEnum {
    STUDY("Учиться 📚"),
    CHAT("Общение 💬"),
    PROFILE("Профиль 👤");

    private final String text;

    MainMenuButtonEnum(String text) {
        this.text = text;
    }
}
