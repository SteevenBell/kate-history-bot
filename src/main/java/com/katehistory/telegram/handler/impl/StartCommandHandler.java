package com.katehistory.telegram.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.katehistory.service.model.UserService;
import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.keyboard.KeyboardFactory;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StartCommandHandler implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    private final UserService userService;
    private final KeyboardFactory keyboardFactory;

    @Override
    public void handle(TelegramMessage message) {
        Long chatId = message.getChat().getId();
        String firstName = message.getChat().getFirstName();
        var tgUser = message.getFrom();

        // проверяем и если нужно, то регистрируем пользователя
        boolean isNew = userService.registerIfAbsent(tgUser);

        String text = isNew
                ? """
                🎉 Добро пожаловать, %s!
                Здесь ты найдёшь материалы по истории Беларуси и
                обществоведению, а также тесты и курсы к ним.
                Нажми «Учиться 📚» и начнём!
                """.formatted(firstName)
                : """
                👋 С возвращением, %s!
                Чем хочешь заняться?
                """.formatted(firstName);

        try {
            String mainMenu = keyboardFactory.getCompactMainMenu();

            telegramBotClient.sendMessage(chatId, text, mainMenu);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при сборке главного меню", e);
            telegramBotClient.sendMessage(chatId, "Произошла ошибка. Обратитесь в поддержку.");
        }
    }

    @Override
    public String getCommand() {
        return "/start";
    }
}
