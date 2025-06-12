package com.katehistory.telegram.handler;

import com.katehistory.telegram.model.TelegramMessage;


/**
 * Обработчик текстовой команды от пользователя (reply-кнопки или ввода текста).
 */
public interface TelegramCommandHandler {

    /**
     * Метод вызывается при получении команды/текста, зарегистрированного в dispatcher'е.
     *
     * @param message сообщение Telegram, содержащее текст команды.
     */
    void handle(TelegramMessage message);

    /**
     * Ключ команды, по которому dispatcher определяет, какому handler'у передать обработку.
     * Например: \"/start\", \"Бесплатные материалы\", \"Пройти тест\" и т.д.
     *
     * @return команда (ключ для сопоставления в dispatcher'е).
     */
    String getCommand();
}
