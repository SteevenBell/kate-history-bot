/**
 * Полностью отрефакторенный диспетчер Telegram-бота
 * Обрабатывает команды и callback-и
 * Команды регистрируются через TelegramCommandHandler
 */

package com.katehistory.telegram.dispatcher;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCallbackHandler;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.model.TelegramCallbackQuery;
import com.katehistory.telegram.model.TelegramMessage;
import com.katehistory.telegram.model.TelegramUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TelegramUpdateDispatcher {
    private final TelegramBotClient telegramBotClient;
    private final Map<String, TelegramCommandHandler> commandHandlers = new HashMap<>();
    private final TelegramCallbackHandler callbackHandler;

    public TelegramUpdateDispatcher(TelegramBotClient telegramBotClient,
                                    List<TelegramCommandHandler> handlers,
                                    TelegramCallbackHandler callbackHandler) {
        this.telegramBotClient = telegramBotClient;
        this.callbackHandler = callbackHandler;
        for (TelegramCommandHandler handler : handlers) {
            commandHandlers.put(handler.getCommand(), handler);
        }
    }

    public void dispatch(List<TelegramUpdate> updates) {
        for (TelegramUpdate update : updates) {
            try {
                if (update.getMessage() != null && update.getMessage().getText() != null) {
                    handleTextMessage(update.getMessage());
                } else if (update.getCallbackQuery() != null) {
                    handleCallback(update.getCallbackQuery());
                }
            } catch (Exception e) {
                log.error("Ошибка обработки обновления {}", update, e);
            }
        }
    }

    private void handleTextMessage(TelegramMessage message) {
        String text = message.getText();
        Long chatId = message.getChat().getId();
        TelegramCommandHandler handler = commandHandlers.get(text);

        if (handler != null) {
            handler.handle(message);
        } else {
            telegramBotClient.sendMessage(chatId, "Неизвестная команда: " + text);
        }
    }

    private void handleCallback(TelegramCallbackQuery callback) {
        String callbackData = callback.getData();
        Long messageId = callback.getMessage().getMessageId();
        Long chatId = callback.getMessage().getChat().getId();

        if (!callbackHandler.handleCallback(chatId, callbackData, messageId)) {
            telegramBotClient.sendMessage(chatId, "Неизвестный callback: " + callbackData);
        }
    }
}
