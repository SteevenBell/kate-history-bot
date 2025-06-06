package com.katehistory.telegram.dispatcher;

import com.katehistory.telegram.TelegramBotClient;
import com.katehistory.telegram.handler.TelegramCommandHandler;
import com.katehistory.telegram.handler.impl.FreeMaterialsCommandHandler;
import com.katehistory.telegram.handler.impl.StartCommandHandler;
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

    public TelegramUpdateDispatcher(TelegramBotClient telegramBotClient,
                                    StartCommandHandler startHandler,
                                    FreeMaterialsCommandHandler freeMaterialsCommandHandler) {
        this.telegramBotClient = telegramBotClient;

        commandHandlers.put("/start", startHandler);
        // Регистрируем обработчик для пункта "Бесплатные материалы"
        commandHandlers.put("Бесплатные материалы", freeMaterialsCommandHandler);
    }

    public void dispatch(List<TelegramUpdate> updates) {
        for (TelegramUpdate update : updates) {
            // Обрабатываем текстовые сообщения
            if (update.getMessage() != null && update.getMessage().getText() != null) {
                TelegramMessage message = update.getMessage();
                String text = message.getText();
                Long chatId = message.getChat().getId();
                TelegramCommandHandler handler = commandHandlers.get(text);
                if (handler != null) {
                    handler.handle(message);
                } else {
                    try {
                        telegramBotClient.sendMessage(chatId, "Неизвестная команда: " + text, null);
                    } catch (Exception e) {
                        log.error("Ошибка отправки сообщения для неизвестной команды", e);
                    }
                }
            }
            // Обработка inline callback'ов остается без изменений
            if (update.getCallbackQuery() != null) {
                TelegramCallbackQuery callback = update.getCallbackQuery();
                String callbackData = callback.getData();
                Long chatId = callback.getMessage().getChat().getId();
                try {
                    handleCallback(chatId, callbackData);
                } catch (Exception e) {
                    log.error("Ошибка при обработке callback для chatId {}: {}", chatId, e.getMessage());
                }
            }
        }
    }

    private void handleCallback(Long chatId, String callbackData) throws Exception {
        if (callbackData.equals("back_to_menu")) {
            telegramBotClient.sendMainMenu(chatId);
        } else {
            telegramBotClient.sendMessage(chatId, "Неизвестный callback: " + callbackData);
        }
    }
}
