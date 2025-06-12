package com.katehistory.telegram.handler.impl;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.model.TelegramMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportCommandHandlerImpl implements TelegramCommandHandler {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void handle(TelegramMessage message) {
        telegramBotClient.sendMessage(message.getChat().getId(), "Если возникли вопросы — напиши сюда: @kate_history_support");
    }

    @Override
    public String getCommand() {
        return "Поддержка";
    }
}
