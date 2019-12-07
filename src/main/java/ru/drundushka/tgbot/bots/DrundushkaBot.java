package ru.drundushka.tgbot.bots;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class DrundushkaBot extends TelegramLongPollingBot {

    public static final String NAME = "DrundushkaBot";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                try {
                    execute(sendMsg(update.getMessage().getChatId().toString(), text));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized SendMessage sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        return sendMessage;
    }

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.getToken(NAME);
    }
}
