package ru.drundushka.tgbot.bots;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class WorkingShiftBot extends TelegramLongPollingBot {

    private static final String NAME = "WorkingShiftBot";

    private State currentState;

    private enum State {
        UNREGISTRED(0),
        REGISTRED(1),
        STARTED(2),
        STOPPED(3);

        int code;

        State(int code) {
            this.code = code;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.getToken(NAME);
    }

    private synchronized SendMessage sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(s);
        return sendMessage;
    }
}
