package com.katehistory.telegram.keyboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum BotButtonEnum {
    // --- Учиться
    FREE_MATERIALS("Бесплатные материалы"),
    TEST_MENU("Пройти тест"),
    COURSES_MENU("Курсы"),

    // --- Общение
    BOOK_LESSON("Записаться на занятие"),
    SUPPORT("Поддержка"),

    // --- Профиль
    ACHIEVEMENTS("Мои достижения"),
    DAILY_TASKS("Ежедневные задания"),

    // --- Служебные
    BACK("⬅️ Назад", "back_to_menu");

    private final String text;
    private final String callback;

    BotButtonEnum(String text) {
        this.text = text;
        this.callback = this.name().toLowerCase();
    }

    public static Optional<BotButtonEnum> fromCallback(String callback) {
        return Arrays.stream(values())
                .filter(item -> item.callback.equals(callback))
                .findFirst();
    }
}
