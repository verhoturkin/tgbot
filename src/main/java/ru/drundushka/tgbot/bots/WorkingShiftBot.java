package ru.drundushka.tgbot.bots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drundushka.tgbot.bots.utils.KeyboardBuilder;
import ru.drundushka.tgbot.model.Profile;
import ru.drundushka.tgbot.repository.ProfileRepository;
import ru.drundushka.tgbot.repository.ShiftRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class WorkingShiftBot extends TelegramLongPollingBot {

    private static final String NAME = "WorkingShiftBot";
    private final ProfileRepository profiles;
    private final ShiftRepository shifts;

    @Autowired
    public WorkingShiftBot(ProfileRepository profiles, ShiftRepository shifts) {
        this.profiles = profiles;
        this.shifts = shifts;
    }

    @Override
    public void onUpdateReceived(Update update) {

        ReplyKeyboardMarkup keyboardMarkup = new KeyboardBuilder()
                .addButton("dddd").addButton("eee")
                .nextRow()
                .addButton("wewe").addButton("sklk")
                .build();
        try {
            execute(sendMsg(update.getMessage().getChatId(), "text")
                    .setReplyMarkup(keyboardMarkup));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message message) {
        Profile profile = profiles.findByTgId(message.getFrom().getId());
        State state = profile == null ? State.UNREGISTRED : State.from(profile.getState());
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

        private final int value;

        private static final Map<Integer, State> map = new HashMap<Integer, State>();
        static
        {
            for (State state : State.values())
                map.put(state.value, state);
        }

        public static State from(int value)
        {
            return map.get(value);
        }

        State(int value) {
            this.value = value;
        }

    }
}
