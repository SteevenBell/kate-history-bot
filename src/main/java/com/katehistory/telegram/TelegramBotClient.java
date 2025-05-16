package com.katehistory.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TelegramBotClient {
    @Value("${telegram.bot.token}")
    private String botToken;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Базовый URL
    private String getApiUrl(String method) {
        return "https://api.telegram.org/bot" + botToken + "/" + method;
    }

    // Отправка сообщения
    public void sendMessage(Long chatId, String text) throws Exception {
        String json = String.format("{\"chat_id\":%d,\"text\":\"%s\"}", chatId, text);

        sendPost("sendMessage", json);
    }

    // Пример метода для получения обновлений (Long Polling)
    public Map<String, Object> getUpdates(int offset) throws Exception {
        String url = getApiUrl("getUpdates") + "?offset=" + offset;
        return sendGet(url);
    }

    // Выполняем GET-запрос
    private Map<String, Object> sendGet(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return objectMapper.readValue(response.toString(), Map.class);
    }

    // Выполняем POST-запрос
    private void sendPost(String method, String jsonBody) throws Exception {
        URL url = new URL(getApiUrl(method));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() != 200) {
            System.out.println("Ошибка при выполнении запроса к Telegram");
        }
    }
}
