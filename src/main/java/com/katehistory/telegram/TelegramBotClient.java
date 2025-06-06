package com.katehistory.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katehistory.model.Test;
import com.katehistory.telegram.keyboard.KeyboardFactory;
import com.katehistory.telegram.model.TelegramUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBotClient {
    @Value("${telegram.bot.token}")
    private String botToken;

    private final ObjectMapper objectMapper; // Инжектируем ObjectMapper
    private final KeyboardFactory keyboardFactory;
    private final RestTemplate restTemplate; // Инжектируем RestTemplate

    private String getApiUrl(String method) {
        return "https://api.telegram.org/bot" + botToken + "/" + method;
    }

    // Основной метод для отправки POST-запросов
    private void executePost(String method, Map<String, Object> body) {
        String url = getApiUrl(method);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Ошибка при вызове Telegram API [{}]: статус-код {}, тело ответа: {}",
                        method, response.getStatusCodeValue(), response.getBody());
            } else {
                log.debug("Вызов Telegram API [{}] успешно выполнен. Ответ: {}", method, response.getBody());
            }
        } catch (HttpClientErrorException e) {
            log.error("Ошибка клиента при вызове Telegram API [{}]: статус-код {}, тело ответа: {}",
                    method, e.getStatusCode(), e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("Исключение при вызове Telegram API [{}]", method, e);
        }
    }

    public void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    public void sendMessage(Long chatId, String text, String replyMarkupJson) {
        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", text);

        if (replyMarkupJson != null && !replyMarkupJson.isEmpty()) {
            try {
                // Преобразуем JSON-строку клавиатуры в объект, чтобы RestTemplate корректно ее сериализовал
                // как часть основного JSON-тела запроса
                body.put("reply_markup", objectMapper.readValue(replyMarkupJson, Object.class));
            } catch (JsonProcessingException e) {
                log.error("Ошибка парсинга JSON для reply_markup: {}", replyMarkupJson, e);
                // Можно либо отправить сообщение без клавиатуры, либо не отправлять вообще
                // Здесь для примера отправляем без клавиатуры:
                // body.remove("reply_markup");
                // Или выбрасываем исключение / возвращаем флаг ошибки
                return; // или throw new RuntimeException("Ошибка подготовки сообщения", e);
            }
        }
        executePost("sendMessage", body);
    }

    public void sendMainMenu(Long chatId) {
        try {
            String keyboardJson = keyboardFactory.getMainMenu();
            Map<String, Object> body = new HashMap<>();
            body.put("chat_id", chatId);
            body.put("text", "Выберите действие:");
            body.put("reply_markup", objectMapper.readValue(keyboardJson, Object.class));
            executePost("sendMessage", body);
        } catch (JsonProcessingException e) {
            log.error("Ошибка подготовки главного меню для chatId {}: {}", chatId, e.getMessage(), e);
        }
    }

    public void sendTestSubjectMenu(Long chatId) {
        try {
            String keyboardJson = keyboardFactory.getTestSubjectMenu();
            Map<String, Object> body = new HashMap<>();
            body.put("chat_id", chatId);
            body.put("text", "Выберите предмет:");
            body.put("reply_markup", objectMapper.readValue(keyboardJson, Object.class));
            executePost("sendMessage", body);
        } catch (JsonProcessingException e) {
            log.error("Ошибка подготовки меню выбора предмета для chatId {}: {}", chatId, e.getMessage(), e);
        }
    }

    public void sendTestListInline(Long chatId, List<Test> tests) {
        try {
            List<Map<String, Object>> testMaps = tests.stream()
                    .map(test -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", test.getTitle());
                        map.put("id", test.getId()); // Убедитесь, что у Test есть getId()
                        return map;
                    })
                    .collect(Collectors.toList());

            String inlineKeyboardJson = keyboardFactory.getTestListInline(testMaps);
            Map<String, Object> body = new HashMap<>();
            body.put("chat_id", chatId);
            body.put("text", "Выберите тест:");
            body.put("reply_markup", objectMapper.readValue(inlineKeyboardJson, Object.class));
            executePost("sendMessage", body);
        } catch (JsonProcessingException e) {
            log.error("Ошибка подготовки списка тестов для chatId {}: {}", chatId, e.getMessage(), e);
        }
    }

    // Метод getUpdates можно пока оставить как есть, если он работает корректно.
    // В будущем его также можно перевести на RestTemplate.
    public List<TelegramUpdate> getUpdates(int offset) throws Exception { // Оставим throws Exception, т.к. нижестоящие могут его кидать
        String url = getApiUrl("getUpdates") + "?offset=" + offset + "&timeout=25"; // Добавлен timeout для long polling

        // Для getUpdates предпочтительнее использовать RestTemplate для единообразия
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
                return parseUpdates(responseMap);
            } else {
                log.error("Ошибка при получении обновлений: статус {}, тело ответа {}", response.getStatusCode(), response.getBody());
                return List.of(); // Возвращаем пустой список в случае ошибки
            }
        } catch (HttpClientErrorException e) {
            log.error("Ошибка клиента при получении обновлений: статус {}, тело ответа: {}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            return List.of();
        } catch (Exception e) {
            log.error("Исключение при получении обновлений", e);
            throw e; // или return List.of(); в зависимости от желаемой стратегии обработки
        }
    }

    private List<TelegramUpdate> parseUpdates(Map<String, Object> responseMap) {
        if (responseMap == null || !responseMap.containsKey("result")) {
            log.warn("Ответ от getUpdates не содержит 'result' или пустой.");
            return List.of();
        }
        List<Map<String, Object>> rawUpdates = (List<Map<String, Object>>) responseMap.get("result");
        if (rawUpdates == null) {
            log.warn("'result' в ответе от getUpdates равен null.");
            return List.of();
        }
        return rawUpdates.stream()
                .map(item -> {
                    try {
                        return objectMapper.convertValue(item, TelegramUpdate.class);
                    } catch (IllegalArgumentException e) {
                        log.error("Ошибка конвертации объекта TelegramUpdate: {}", item, e);
                        return null; // Пропускаем некорректный update
                    }
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }
}
