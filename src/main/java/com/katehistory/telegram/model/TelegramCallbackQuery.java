package com.katehistory.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramCallbackQuery {
    private String id;
    private TelegramUser from;
    private TelegramMessage message;

    @JsonProperty("data")
    private String data;
}
