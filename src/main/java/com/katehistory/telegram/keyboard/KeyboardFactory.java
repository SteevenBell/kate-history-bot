package com.katehistory.telegram.keyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Это основной класс, который будет генерировать JSON для reply_markup
 */

@Component
public class KeyboardFactory {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Главное меню с reply_keyboard
    public String getMainMenu() throws JsonProcessingException {
        List<List<String>> keyboard = new ArrayList<>();
        // Допустим, добавляем кнопку "Бесплатные материалы"
        keyboard.add(Arrays.asList("Бесплатные материалы", "Пройти тест"));
        keyboard.add(Arrays.asList("Записаться на занятие", "Курсы и оплата"));
        keyboard.add(Arrays.asList("Мои достижения", "Поддержка", "Игра / ежедневные задания"));

        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("keyboard", keyboard);
        replyMarkup.put("resize_keyboard", true);
        replyMarkup.put("one_time_keyboard", false);
        replyMarkup.put("selective", true);

        return objectMapper.writeValueAsString(replyMarkup);
    }

    // Меню тестирования (выбор предмета)
    public String getTestSubjectMenu() throws JsonProcessingException {
        List<List<String>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList("История Беларуси"));
        keyboard.add(Collections.singletonList("Обществоведение"));
        keyboard.add(Collections.singletonList("⬅️ Назад"));

        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("keyboard", keyboard);
        replyMarkup.put("resize_keyboard", true);
        replyMarkup.put("one_time_keyboard", true);
        replyMarkup.put("selective", false);

        return objectMapper.writeValueAsString(replyMarkup);
    }

    // Кнопка "Назад" в reply_keyboard
    public String getBackToMenuKeyboard() throws JsonProcessingException {
        List<List<String>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList("⬅️ Назад в меню"));

        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("keyboard", keyboard);
        replyMarkup.put("resize_keyboard", true);
        replyMarkup.put("one_time_keyboard", true);

        return objectMapper.writeValueAsString(replyMarkup);
    }

    // Inline-клавиатура для списка тестов
    public String getTestListInline(List<Map<String, Object>> tests) throws JsonProcessingException {
        List<List<Map<String, Object>>> inlineKeyboard = new ArrayList<>();

        for (Map<String, Object> test : tests) {
            String title = (String) test.get("title");
            Long id = (Long) test.get("id");

            Map<String, Object> button = new HashMap<>();
            button.put("text", title);
            button.put("callback_data", "test_" + id);

            inlineKeyboard.add(Collections.singletonList(button));
        }

        // Добавляем кнопку "Назад"
        Map<String, Object> backBtn = new HashMap<>();
        backBtn.put("text", "⬅️ Назад");
        backBtn.put("callback_data", "back_to_menu");

        inlineKeyboard.add(Collections.singletonList(backBtn));

        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("inline_keyboard", inlineKeyboard);

        return objectMapper.writeValueAsString(replyMarkup);
    }

    // Упрощённый метод для force_reply
    public String getForceReplyKeyboard() throws JsonProcessingException {
        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("force_reply", true);
        replyMarkup.put("input_field_placeholder", "Введите ваш ответ...");

        return objectMapper.writeValueAsString(replyMarkup);
    }

    // Пустая клавиатура (для скрытия)
    public String getEmptyKeyboard() throws JsonProcessingException {
        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("remove_keyboard", true);

        return objectMapper.writeValueAsString(replyMarkup);
    }

    // Новый метод для меню "Бесплатные материалы"
    public String getFreeMaterialsMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> inlineKeyboard = new ArrayList<>();

        // Кнопка "Конспекты (PDF)"
        Map<String, Object> pdfButton = new HashMap<>();
        pdfButton.put("text", "Конспекты (PDF)");
        pdfButton.put("callback_data", "free_materials_pdf");

        // Кнопка "Карточки (текст/фото)"
        Map<String, Object> cardsButton = new HashMap<>();
        cardsButton.put("text", "Карточки (текст/фото)");
        cardsButton.put("callback_data", "free_materials_cards");

        // Кнопка "Тематические статьи"
        Map<String, Object> articlesButton = new HashMap<>();
        articlesButton.put("text", "Тематические статьи");
        articlesButton.put("callback_data", "free_materials_articles");

        // Кнопка "⬅️ Назад" с callback_data
        Map<String, Object> backButton = new HashMap<>();
        backButton.put("text", "⬅️ Назад");
        backButton.put("callback_data", "back_to_menu");

        inlineKeyboard.add(Collections.singletonList(pdfButton));
        inlineKeyboard.add(Collections.singletonList(cardsButton));
        inlineKeyboard.add(Collections.singletonList(articlesButton));
        inlineKeyboard.add(Collections.singletonList(backButton));

        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("inline_keyboard", inlineKeyboard);

        // Используем существующий objectMapper
        return this.objectMapper.writeValueAsString(replyMarkup);
    }
}
