package com.katehistory.telegram.handler.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameCommandHandlerImpl implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void handle(TelegramMessage message) {
//        telegramBotClient.sendGameMenu(message.getChat().getId());
    }

    @Override
    public String getCommand() {
        return "Игра / ежедневные задания";
    }
}
