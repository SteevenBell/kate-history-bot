package com.katehistory.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;
    private final KeyboardFactory keyboardFactory;
    private final RestTemplate restTemplate;

    private String getApiUrl(String method) {
        return "https://api.telegram.org/bot" + botToken + "/" + method;
    }

    public void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    public void sendMessage(Long chatId, String text, String replyMarkupJson) {
        try {
            Object replyMarkup = (replyMarkupJson != null && !replyMarkupJson.isEmpty())
                    ? objectMapper.readValue(replyMarkupJson, Object.class)
                    : null;

            Map<String, Object> body = buildMessageBody(chatId, text, replyMarkup);
            executePost("sendMessage", body);
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга JSON для reply_markup: {}", replyMarkupJson, e);
        }
    }

    public void sendMainMenu(Long chatId) {
        try {
            String keyboardJson = keyboardFactory.getCompactMainMenu();
            sendMessage(chatId, "Главное меню:", keyboardJson);
        } catch (JsonProcessingException e) {
            log.error("Ошибка подготовки главного меню", e);
        }
    }

    public void sendStudyMenu(Long chatId) {
        try {
            String keyboardJson = keyboardFactory.getStudyMenu();
            sendMessage(chatId, "Учебное меню:", keyboardJson);
        } catch (JsonProcessingException e) {
            log.error("Ошибка подготовки учебного меню", e);
        }
    }

    public void sendCommunicationMenu(Long chatId) {
        try {
            String keyboardJson = keyboardFactory.getCommunicationMenu();
            sendMessage(chatId, "Общение и поддержка:", keyboardJson);
        } catch (JsonProcessingException e) {
            log.error("Ошибка подготовки меню общения", e);
        }
    }

    public void sendProfileMenu(Long chatId) {
        try {
            String keyboardJson = keyboardFactory.getProfileMenu();
            sendMessage(chatId, "Профиль пользователя:", keyboardJson);
        } catch (JsonProcessingException e) {
            log.error("Ошибка подготовки меню профиля", e);
        }
    }

//    public void sendFreeMaterialsMenu(Long chatId) {
//        try {
//            String keyboardJson = keyboardFactory.getFreeMaterialsMenu();
//            sendMessage(chatId, "Выберите раздел бесплатных материалов:", keyboardJson);
//        } catch (JsonProcessingException e) {
//            log.error("Ошибка подготовки меню бесплатных материалов", e);
//        }
//    }
//
//    public void sendTestMenu(Long chatId) {
//        try {
//            String keyboardJson = keyboardFactory.getTestMenu();
//            sendMessage(chatId, "Выберите тест:", keyboardJson);
//        } catch (JsonProcessingException e) {
//            log.error("Ошибка подготовки меню тестов", e);
//        }
//    }
//
//    public void sendCoursesMenu(Long chatId) {
//        try {
//            String keyboardJson = keyboardFactory.getCoursesMenu();
//            sendMessage(chatId, "Каталог доступных курсов:", keyboardJson);
//        } catch (JsonProcessingException e) {
//            log.error("Ошибка подготовки меню курсов", e);
//        }
//    }
//
//    public void sendGameMenu(Long chatId) {
//        try {
//            String keyboardJson = keyboardFactory.getGameMenu();
//            sendMessage(chatId, "Выбери задание на сегодня:", keyboardJson);
//        } catch (JsonProcessingException e) {
//            log.error("Ошибка подготовки меню игровых заданий", e);
//        }
//    }

    private Map<String, Object> buildMessageBody(Long chatId, String text, Object replyMarkup) {
        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", chatId);
        body.put("text", text);
        if (replyMarkup != null) {
            body.put("reply_markup", replyMarkup);
        }
        return body;
    }

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

    public List<TelegramUpdate> getUpdates(int offset) throws Exception {
        String url = getApiUrl("getUpdates") + "?offset=" + offset + "&timeout=25";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
                return parseUpdates(responseMap);
            } else {
                log.error("Ошибка при получении обновлений: статус {}, тело ответа {}", response.getStatusCode(), response.getBody());
                return List.of();
            }
        } catch (HttpClientErrorException e) {
            log.error("Ошибка клиента при получении обновлений: статус {}, тело ответа: {}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            return List.of();
        } catch (Exception e) {
            log.error("Исключение при получении обновлений", e);
            throw e;
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
                        return null;
                    }
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }
}
