package com.katehistory.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramMessage {
    @JsonProperty("message_id")
    private Long messageId;
    private TelegramUser from;
    private TelegramChat chat;
    private String text;
}
