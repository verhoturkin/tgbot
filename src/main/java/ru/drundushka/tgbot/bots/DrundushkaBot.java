package ru.drundushka.tgbot.bots;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drundushka.tgbot.commands.CokesCommand;
import ru.drundushka.tgbot.commands.StartCommand;

@Component
public class DrundushkaBot extends TelegramLongPollingCommandBot {

    public static final String NAME = "DrundushkaBot";

    public DrundushkaBot(StartCommand startCommand) {
        super(NAME);
        register(new CokesCommand());
        register(startCommand);
    }

    private synchronized SendMessage sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        return sendMessage;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
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

    @Override
    protected void processInvalidCommandUpdate(Update update) {
        try {
            execute(sendMsg(update.getMessage().getChatId().toString(), "Команда не найдена"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BotConfig.getToken(NAME);
    }
}
