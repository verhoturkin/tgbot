package ru.drundushka.tgbot.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drundushka.tgbot.bots.utils.KeyboardBuilder;
import ru.drundushka.tgbot.repository.ProfileRepository;

@Component
public class WorkingShiftBot extends TelegramLongPollingBot {

    private static final String NAME = "WorkingShiftBot";
    private final ProfileRepository repository;

    @Autowired
    public WorkingShiftBot(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder()
                .addButton("dddd")
                .addButton("eee")
                .nextRow()
                .addButton("wewe")
                .addButton("sklk");
        ReplyKeyboardMarkup keyboardMarkup = keyboardBuilder.build();
        try {
            execute(sendMsg(update.getMessage().getChatId(), "text")
                    .setReplyMarkup(keyboardMarkup));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.getToken(NAME);
    }

    private synchronized SendMessage sendMsg(Long chatId, String s) {
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(s);
        return sendMessage;
    }

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
}
