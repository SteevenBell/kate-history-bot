package com.katehistory.telegram;

import com.katehistory.telegram.dispatcher.TelegramUpdateDispatcher;
import com.katehistory.telegram.model.TelegramUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramUpdateService implements ApplicationRunner {
    private final TelegramBotClient telegramBotClient;
    private final TelegramUpdateDispatcher dispatcher;
    private final AtomicInteger lastUpdateId = new AtomicInteger(0);
    private volatile boolean running = true;

    @Override
    public void run(ApplicationArguments args) {
        Thread pollingThread = new Thread(this::pollUpdates, "telegram-polling-thread");
        pollingThread.setDaemon(true);
        pollingThread.start();
        log.info("Polling Telegram API запущен...");
    }

    private void pollUpdates() {
        while (running) {
            try {
                List<TelegramUpdate> updates = telegramBotClient.getUpdates(lastUpdateId.get());

                if (updates != null && !updates.isEmpty()) {
                    dispatcher.dispatch(updates);

                    int latestUpdateId = updates.stream()
                            .mapToInt(TelegramUpdate::getUpdateId)
                            .max()
                            .orElse(lastUpdateId.get());

                    lastUpdateId.set(latestUpdateId + 1);
                }
            } catch (Exception e) {
                log.error("Ошибка при получении обновлений", e);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void stopPolling() {
        running = false;
        log.info("Polling Telegram API остановлен.");
    }
}
