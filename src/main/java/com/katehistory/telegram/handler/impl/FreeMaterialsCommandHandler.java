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
public class FreeMaterialsCommandHandler implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void handle(TelegramMessage message) {
        Long chatId = message.getChat().getId();
        try {
            telegramBotClient.sendFreeMaterialsMenu(message.getChat().getId());
        } catch (Exception e) {
            log.error("Ошибка отправки меню бесплатных материалов для chatId: " + chatId, e);
        }
    }

    @Override
    public String getCommand() {
        return "Бесплатные материалы";
    }
}
