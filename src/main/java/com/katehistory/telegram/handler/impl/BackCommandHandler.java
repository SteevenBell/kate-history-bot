package com.katehistory.telegram.handler.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackCommandHandler implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void handle(TelegramMessage message) {
        Long chatId = message.getChat().getId();
        try {
            // В данном простом примере возвращаем пользователя в главное меню
            telegramBotClient.sendMainMenu(chatId);
        } catch (Exception e) {
            log.error("Ошибка при возврате в главное меню для chatId " + chatId, e);
        }
    }
}
