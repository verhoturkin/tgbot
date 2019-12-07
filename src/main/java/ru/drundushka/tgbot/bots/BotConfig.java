package ru.drundushka.tgbot.bots;

import java.util.Map;

class BotConfig {

    private static final Map<String, String> TOKENS = System.getenv();

    private BotConfig() {
    }

    static String getToken(String botName) {
        return TOKENS.get(botName);
    }
}
