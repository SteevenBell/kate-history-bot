package com.katehistory.telegram.handler.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCallbackHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramCallbackHandlerImpl implements TelegramCallbackHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public boolean handleCallback(Long chatId, String callbackData) {
        return switch (callbackData) {
            case "free_materials_pdf" -> {
                telegramBotClient.sendMessage(chatId, "Скоро здесь появятся PDF-конспекты 📖");
                yield true;
            }
            case "free_materials_cards" -> {
                telegramBotClient.sendMessage(chatId, "Карточки с фактами по истории находятся в разработке 🤨");
                yield true;
            }
            case "free_materials_articles" -> {
                telegramBotClient.sendMessage(chatId, "Тематические статьи будут доступны позже ✍️");
                yield true;
            }
            case "test_topic1" -> {
                telegramBotClient.sendMessage(chatId, "Вы начали тест по теме 1 (заглушка) ✅");
                yield true;
            }
            case "test_topic2" -> {
                telegramBotClient.sendMessage(chatId, "Вы начали тест по теме 2 (заглушка) ✅");
                yield true;
            }
            case "course_1" -> {
                telegramBotClient.sendMessage(chatId, "Курс 1: История до XX века — в разработке 📚");
                yield true;
            }
            case "course_2" -> {
                telegramBotClient.sendMessage(chatId, "Курс 2: Новейшее время — в разработке 🕰️");
                yield true;
            }
            case "daily_question" -> {
                telegramBotClient.sendMessage(chatId, "Вопрос дня: (скоро появится) ❓");
                yield true;
            }
            case "daily_card" -> {
                telegramBotClient.sendMessage(chatId, "Флешкарта дня: (скоро появится) 🧠");
                yield true;
            }
            case "back_to_menu" -> {
                telegramBotClient.sendMainMenu(chatId);
                yield true;
            }
            default -> {
                log.warn("Неизвестный callback: {}", callbackData);
                yield false;
            }
        };
    }
}
