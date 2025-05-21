package com.katehistory.telegram;

import com.katehistory.telegram.dispatcher.TelegramUpdateDispatcher;
import com.katehistory.telegram.model.TelegramUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TelegramUpdateService {
    private final TelegramBotClient telegramBotClient;
    private final TelegramUpdateDispatcher dispatcher;
    private final AtomicInteger lastUpdateId = new AtomicInteger(0);


    public void startPolling() {
        new Thread(() -> {
            while (true) {
                try {
                    List<TelegramUpdate> updates = telegramBotClient.getUpdates(lastUpdateId.get());

                    if (updates != null && !updates.isEmpty()) {
                        // Передаём весь список в диспетчер
                        dispatcher.dispatch(updates);

                        // Обновляем offset после обработки всех update_id
                        int latestUpdateId = updates.stream()
                                .mapToInt(TelegramUpdate::getUpdateId)
                                .max()
                                .orElse(lastUpdateId.get());

                        lastUpdateId.set(latestUpdateId + 1);
                    }

                } catch (Exception e) {
                    System.err.println("Ошибка при получении обновлений: " + e.getMessage());
                }

                try {
                    Thread.sleep(1000); // Ожидание перед следующим опросом
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    private void handleIncomingMessage(Map<String, Object> message) {
        Map<String, Object> chat = (Map<String, Object>) message.get("chat");
        String text = (String) message.get("text");
        Long chatId = ((Number) chat.get("id")).longValue();

        try {
            if ("/start".equals(text)) {
                telegramBotClient.sendMessage(chatId, "Добро пожаловать в бота по истории Беларуси!");
            } else {
                telegramBotClient.sendMessage(chatId, "Вы написали: " + text);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при отправке сообщения!");
        }
    }
}
