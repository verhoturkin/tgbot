package ru.drundushka.tgbot.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drundushka.tgbot.commands.StartCommand;
import ru.drundushka.tgbot.commands.StopCommand;

@Component
public class WorkingShiftBot extends TelegramLongPollingCommandBot {

    public static final String NAME = "WorkingShiftBot";

    @Autowired
    public WorkingShiftBot(StartCommand startCommand,
                           StopCommand stopCommand) {
        super(NAME);
        register(startCommand);
        register(stopCommand);
    }

    @Override
    public String getBotToken() {
        return BotConfig.getToken(NAME);
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

    private synchronized SendMessage sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        return sendMessage;
    }
}
