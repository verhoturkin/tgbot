package ru.drundushka.tgbot.bots;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drundushka.tgbot.bots.util.KeyboardBuilder;
import ru.drundushka.tgbot.service.ProfileService;

import java.util.HashMap;
import java.util.Map;

@Component
public class WorkingShiftBot extends TelegramLongPollingBot {

    private static final String NAME = "WorkingShiftBot";
    private final ProfileService profiles;

    public WorkingShiftBot(ProfileService profiles) {
        this.profiles = profiles;

    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                handleMessage(message);
            }
        }
    }

    private void handleMessage(Message message) {
        State state = State.from(profiles.getStateById(message.getFrom().getId()));
        SendMessage sendMessage;

        switch (state) {
            case UNREGISTRED:
                sendMessage = messageUnregistered(message);
                break;
            case REGISTRED:
                sendMessage = messageRegistered(message);
                break;
            case STARTED:
                sendMessage = messageStarted(message);
                break;
            case STOPPED:
                sendMessage = messageStopped(message);
                break;
            default:
                sendMessage = messageDefault(message);
        }

        sendMessage.setChatId(message.getChatId())
                .setReplyToMessageId(message.getMessageId())
                .enableMarkdown(true);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage messageDefault(Message message) {
        SendMessage sendMessage = new SendMessage();
    }

    private SendMessage messageStopped(Message message) {
        SendMessage sendMessage = new SendMessage();

        if (message.getText().startsWith("Start")) {
            profiles.setState(message.getFrom().getId(), State.valueOf(State.STARTED));
            sendMessage
                    .setText("Started")
                    .setReplyMarkup(getStartedKeyboard());
        }
        return sendMessage;
    }

    private SendMessage messageStarted(Message message) {
        SendMessage sendMessage = new SendMessage();

        if (message.getText().startsWith("Stop")) {
            profiles.setState(message.getFrom().getId(), State.valueOf(State.STOPPED));
            sendMessage
                    .setText("Stopped")
                    .setReplyMarkup(getStoppedKeyboard());
        }
        return sendMessage;
    }

    private SendMessage messageRegistered(Message message) {
        SendMessage sendMessage = new SendMessage();

        if (message.getText().startsWith("Start")) {
            profiles.setState(message.getFrom().getId(), State.valueOf(State.STARTED));
            sendMessage
                    .setText("Started")
                    .setReplyMarkup(getStartedKeyboard());
        }
        return sendMessage;
    }

    private SendMessage messageUnregistered(Message message) {
        SendMessage sendMessage = new SendMessage();

        if (message.getText().startsWith("Register")) {
            profiles.register(message.getFrom());
            sendMessage
                    .setText("Registered")
                    .setReplyMarkup(getStoppedKeyboard());
        } else {
            sendMessage
                    .setText("Must register")
                    .setReplyMarkup(getRegisterKeyboard());
        }
        return sendMessage;
    }

    private ReplyKeyboardMarkup getStoppedKeyboard() {
        return new KeyboardBuilder()
                .addButton("Start").nextRow()
                .addButton("working days")
                .build();
    }

    private ReplyKeyboardMarkup getStartedKeyboard() {
        return new KeyboardBuilder()
                .addButton("Stop").nextRow()
                .addButton("working days")
                .build();
    }

    private ReplyKeyboardMarkup getRegisterKeyboard() {
        return new KeyboardBuilder()
                .addButton("Register").build();
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

        private static final Map<Integer, State> map = new HashMap<Integer, State>();

        static {
            for (State state : State.values())
                map.put(state.value, state);
        }

        private final int value;

        State(int value) {
            this.value = value;
        }

        public static State from(int value) {
            return map.get(value);
        }

        public static int valueOf(State state) {
            return state.value;
        }

    }
}
