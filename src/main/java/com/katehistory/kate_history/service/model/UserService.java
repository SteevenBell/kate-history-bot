package com.katehistory.kate_history.service.model;

import com.katehistory.kate_history.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserByTelegramId(Long telegramId);

    User saveUser(User user);

    boolean existsByTelegramId(Long telegramId);
}
