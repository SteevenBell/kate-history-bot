package com.katehistory.telegram.handler.message.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.keyboard.enums.MainMenuButtonEnum;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyMenuMessageHandler implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void handle(TelegramMessage message) {
        Long chatId = message.getChat().getId();

        try {
            telegramBotClient.sendStudyMenu(chatId);
        } catch (Exception e) {
            log.error("Ошибка при отправке меню Учиться для chatId: " + chatId, e);
        }
    }

    @Override
    public String getCommand() {
        return MainMenuButtonEnum.STUDY.getText();
    }
}
