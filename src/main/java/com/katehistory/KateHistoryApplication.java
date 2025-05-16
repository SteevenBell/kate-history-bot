package com.katehistory;

import com.katehistory.telegram.TelegramUpdateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KateHistoryApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KateHistoryApplication.class, args);
		TelegramUpdateService telegramUpdateService = context.getBean(TelegramUpdateService.class);
		telegramUpdateService.startPolling(); // запускаем polling
	}

}
