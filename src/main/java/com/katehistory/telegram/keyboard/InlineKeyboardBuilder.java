package com.katehistory.telegram.keyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katehistory.telegram.keyboard.enums.BotButtonEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class InlineKeyboardBuilder {
    private final List<List<Map<String, Object>>> rows = new ArrayList<>();
    private List<Map<String, Object>> currentRow = new ArrayList<>();

    public static InlineKeyboardBuilder create() {
        return new InlineKeyboardBuilder();
    }

    public InlineKeyboardBuilder button(String text, String callback) {
        currentRow.add(Map.of("text", text, "callback_data", callback));
        return this;
    }

    public InlineKeyboardBuilder button(BotButtonEnum btn) {
        return button(btn.getText(), btn.getCallback());
    }

    public InlineKeyboardBuilder newRow() {
        if (!currentRow.isEmpty()) {
            rows.add(currentRow);
            currentRow = new ArrayList<>();
        }
        return this;
    }

    public String build(ObjectMapper mapper) throws JsonProcessingException {
        if (!currentRow.isEmpty()) newRow();

        Map<String, Object> markup = Map.of(
                "inline_keyboard", rows,
                "resize_keyboard", true,
                "one_time_keyboard", false
        );

        return mapper.writeValueAsString(markup);
    }
}
