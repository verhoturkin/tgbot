package ru.drundushka.tgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TgbotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(TgbotApplication.class, args);
    }

}
