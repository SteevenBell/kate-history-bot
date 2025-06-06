package com.katehistory.telegram.handler.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.keyboard.KeyboardFactory;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FreeMaterialsCommandHandler implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;
    private final KeyboardFactory keyboardFactory;
    @Override
    public void handle(TelegramMessage message) {
        Long chatId = message.getChat().getId();
        try {
            // Получаем JSON для меню "Бесплатные материалы"
            String keyboardJson = keyboardFactory.getFreeMaterialsMenu();
            // Отправляем сообщение с новым меню
            telegramBotClient.sendMessage(chatId, "Выберите раздел бесплатных материалов:", keyboardJson);
        } catch (Exception e) {
            log.error("Ошибка отправки меню бесплатных материалов для chatId: " + chatId, e);
        }
    }
}
