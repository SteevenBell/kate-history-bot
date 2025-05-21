package com.katehistory.telegram.handler;

import com.katehistory.telegram.model.TelegramMessage;

public interface TelegramCommandHandler {
    void handle(TelegramMessage message);
}
