package com.katehistory.telegram.keyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Это основной класс, который будет генерировать JSON для reply_markup
 */

@Component
public class KeyboardFactory {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getCompactMainMenu() throws JsonProcessingException {
        List<List<String>> keyboard = List.of(
                List.of("Учиться 📚", "Общение 💬", "Профиль 👤")
        );

        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("keyboard", keyboard);
        replyMarkup.put("resize_keyboard", true);
        replyMarkup.put("one_time_keyboard", false);
        return objectMapper.writeValueAsString(replyMarkup);
    }

    // Подменю: Учиться 📚
    public String getStudyMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> keyboard = List.of(
                List.of(button("Бесплатные материалы", "free_materials")),
                List.of(button("Пройти тест", "test_menu")),
                List.of(button("Курсы", "courses_menu")),
                List.of(button("⬅️ Назад", "back_to_menu"))
        );
        return serializeInlineKeyboard(keyboard);
    }

    // Подменю: Общение 💬
    public String getCommunicationMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> keyboard = List.of(
                List.of(button("Записаться на занятие", "book_lesson")),
                List.of(button("Поддержка", "support")),
                List.of(button("⬅️ Назад", "back_to_menu"))
        );
        return serializeInlineKeyboard(keyboard);
    }

    // Подменю: Профиль 👤
    public String getProfileMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> keyboard = List.of(
                List.of(button("Мои достижения", "achievements")),
                List.of(button("Ежедневные задания", "daily_tasks")),
                List.of(button("⬅️ Назад", "back_to_menu"))
        );
        return serializeInlineKeyboard(keyboard);
    }

    /**
     * Главное меню (ReplyKeyboard)
     */
    public String getMainMenu() throws JsonProcessingException {
        List<List<String>> keyboard = List.of(
                List.of("Бесплатные материалы", "Пройти тест"),
                List.of("Записаться на занятие", "Курсы и оплата"),
                List.of("Мои достижения", "Поддержка", "Игра / ежедневные задания")
        );

        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("keyboard", keyboard);
        replyMarkup.put("resize_keyboard", true);
        replyMarkup.put("one_time_keyboard", false);

        return objectMapper.writeValueAsString(replyMarkup);
    }

    public String getFreeMaterialsMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> inlineKeyboard = List.of(
                List.of(button("Конспекты (PDF)", "free_materials_pdf")),
                List.of(button("Карточки (текст/фото)", "free_materials_cards")),
                List.of(button("Тематические статьи", "free_materials_articles")),
                List.of(button("⬅️ Назад", "back_to_menu"))
        );
        return serializeInlineKeyboard(inlineKeyboard);
    }

    public String getTestMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> keyboard = List.of(
                List.of(button("Начать тест по теме 1", "test_topic1")),
                List.of(button("Начать тест по теме 2", "test_topic2")),
                List.of(button("⬅️ Назад", "back_to_menu"))
        );
        return serializeInlineKeyboard(keyboard);
    }

    public String getCoursesMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> keyboard = List.of(
                List.of(button("Курс 1: История до XX века", "course_1")),
                List.of(button("Курс 2: Новейшее время", "course_2")),
                List.of(button("⬅️ Назад", "back_to_menu"))
        );
        return serializeInlineKeyboard(keyboard);
    }

    public String getGameMenu() throws JsonProcessingException {
        List<List<Map<String, Object>>> keyboard = List.of(
                List.of(button("Вопрос дня", "daily_question")),
                List.of(button("Флешкарта дня", "daily_card")),
                List.of(button("⬅️ Назад", "back_to_menu"))
        );
        return serializeInlineKeyboard(keyboard);
    }

    private Map<String, Object> button(String text, String callbackData) {
        Map<String, Object> btn = new HashMap<>();
        btn.put("text", text);
        btn.put("callback_data", callbackData);
        return btn;
    }

    private String serializeInlineKeyboard(List<List<Map<String, Object>>> inlineKeyboard) throws JsonProcessingException {
        Map<String, Object> replyMarkup = new HashMap<>();
        replyMarkup.put("inline_keyboard", inlineKeyboard);
        return objectMapper.writeValueAsString(replyMarkup);
    }
}
