package com.katehistory.telegram.handler.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartCommandHandler implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void handle(TelegramMessage message) {
        Long chatId = message.getChat().getId();
        String firstName = message.getChat().getFirstName();

        try {
            telegramBotClient.sendMessage(chatId, "Добро пожаловать, " + firstName + "! 👩‍🏫");
        } catch (Exception e) {
            System.out.println("Ошибка отправки сообщения");
        }
    }
}
