package com.katehistory.telegram.handler;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.keyboard.KeyboardFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramCallbackHandlerImpl implements TelegramCallbackHandler {
    private final KeyboardFactory keyboardFactory;
    private final TelegramBotClient telegramBotClient;

    @Override
    public boolean handleCallback(Long chatId, String callbackData, Long messageId) {
        return switch (callbackData) {
            case "study_menu" -> {
                telegramBotClient.sendStudyMenu(chatId);
                yield true;
            }
            case "back_study_menu" -> {
                String keyboard = keyboardFactory.getStudyMenu();
                telegramBotClient.editMessageText(chatId, messageId,
                        "Учебное меню:", keyboard);

                yield true;
            }
            case "communication_menu" -> {
                telegramBotClient.sendCommunicationMenu(chatId);
                yield true;
            }
            case "profile_menu" -> {
                telegramBotClient.sendProfileMenu(chatId);
                yield true;
            }
            case "free_materials" -> {
                String keyboard = keyboardFactory.getFreeMaterialsMenu();
                telegramBotClient.editMessageText(chatId, messageId,
                        "Выберите раздел бесплатных материалов:", keyboard);

                yield true;
            }
            case "free_materials_pdf" -> {
                telegramBotClient.editMessageText(chatId, messageId,
                        "Скоро здесь появятся PDF-конспекты 📖", null);
                yield true;
            }
            case "free_materials_cards" -> {
                telegramBotClient.editMessageText(chatId, messageId,
                        "Карточки с фактами по истории находятся в разработке 🤨", null);
                yield true;
            }
            case "free_materials_articles" -> {
                telegramBotClient.editMessageText(chatId, messageId,
                        "Тематические статьи будут доступны позже ✍️", null);
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
