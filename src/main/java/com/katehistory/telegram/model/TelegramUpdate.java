package com.katehistory.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramUpdate {
    @JsonProperty("update_id")
    private Integer updateId;
    @JsonProperty("callback_query")
    private TelegramCallbackQuery callbackQuery;

    private TelegramMessage message;
}
