package com.katehistory.telegram.handler;

public interface TelegramCallbackHandler {
    /**
     * Обрабатывает callback-запрос по данным от inline-кнопки.
     * @param chatId ID чата, из которого пришёл callback
     * @param callbackData строка данных callback'а (например, "back_to_menu")
     * @return true, если callback успешно обработан, иначе false
     */
    boolean handleCallback(Long chatId, String callbackData);
}
