package com.katehistory.telegram.dispatcher;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.handler.impl.StartCommandHandler;
import com.katehistory.telegram.model.TelegramMessage;
import com.katehistory.telegram.model.TelegramUpdate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramUpdateDispatcher {
    private final TelegramBotClient telegramBotClient;
    private final Map<String, TelegramCommandHandler> commandHandlers = new HashMap<>();

    public TelegramUpdateDispatcher(TelegramBotClient telegramBotClient, StartCommandHandler startHandler) {
        this.telegramBotClient = telegramBotClient;

        commandHandlers.put("/start", startHandler);
    }

    public void dispatch(List<TelegramUpdate> updates) {
        for (TelegramUpdate update : updates) {
            TelegramMessage message = update.getMessage();

            if (message != null && message.getText() != null) {
                String text = message.getText();

                TelegramCommandHandler handler = commandHandlers.get(text);
                if (handler != null) {
                    handler.handle(message);
                } else {
                    Long chatId = message.getChat().getId();

                    try {
                        telegramBotClient.sendMessage(chatId, "Неизвестная команда: " + text);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
