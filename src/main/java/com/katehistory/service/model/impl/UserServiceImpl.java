package com.katehistory.service.model.impl;

import com.katehistory.model.User;
import com.katehistory.repository.UserRepository;
import com.katehistory.service.model.UserService;
import com.katehistory.telegram.model.TelegramUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).isPresent();
    }

    @Transactional
    @Override
    public boolean registerIfAbsent(TelegramUser tgUser) {
        return userRepository.findByTelegramId(tgUser.getId())
                .map(u -> false) // уже был
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .telegramId(tgUser.getId())
                            .firstName(tgUser.getFirstName())
                            .username(tgUser.getUsername())
                            .build();
                    userRepository.save(newUser);
                    return true;
                });
    }
}
