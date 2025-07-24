package com.katehistory.service.model;

import com.katehistory.model.User;
import com.katehistory.telegram.model.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserByTelegramId(Long telegramId);

    User saveUser(User user);

    boolean existsByTelegramId(Long telegramId);

    /**
     * Регистрирует пользователя, если он не был зарегистрирован
     * @param tgUser
     * @return true - если создан новый
     *          false - в ином случае
     */
    boolean registerIfAbsent(TelegramUser tgUser);
}
