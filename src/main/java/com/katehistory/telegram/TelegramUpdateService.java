package com.katehistory.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TelegramUpdateService {
    private final TelegramBotClient telegramBotClient;
    private final AtomicInteger lastUpdateId = new AtomicInteger(0);


    public void startPolling() {
        new Thread(() -> {
            try {
                while (true) {
                    Map<String, Object> response = telegramBotClient.getUpdates(lastUpdateId.get());
                    List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("result");

                    for (Map<String, Object> update : results) {
                        int updateId = (int) update.get("update_id");
                        if (update.containsKey("message")) {
                            Map<String, Object> message = (Map<String, Object>) update.get("message");
                            handleIncomingMessage(message);
                        }
                        lastUpdateId.set(updateId + 1);
                    }

                    Thread.sleep(1000); // не спамим
                }
            } catch (Exception e) {
                e.printStackTrace();
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
