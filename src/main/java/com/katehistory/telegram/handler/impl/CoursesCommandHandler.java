package com.katehistory.telegram.handler.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoursesCommandHandler implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void handle(TelegramMessage message) {
//        telegramBotClient.sendCoursesMenu(message.getChat().getId());
    }

    @Override
    public String getCommand() {
        return "Курсы и оплата";
    }
}
