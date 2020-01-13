package ru.drundushka.tgbot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drundushka.tgbot.model.Profile;
import ru.drundushka.tgbot.repository.ProfileRepository;

@Component
public class StartCommand extends BotCommand {

    public static final String UNREG = "Привет %s! Я вас зарегистритовал!";
    public static final String REG = "Привет %s! Вы уже зарегистрированы!";
    public static final String STOPPED = "Привет %s! Ваш профиль снова активен!";

    private final ProfileRepository repository;

    public StartCommand(ProfileRepository repository) {
        super("start", "Start bot and register user");
        this.repository = repository;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());

        Profile current = repository.findByTgId(user.getId());
//                .orElse(new Profile(null,
//                        user.getId(),
//                        user.getFirstName(),
//                        user.getLastName(),
//                        user.getUserName(),
//                        true,
//                        0));

        if (current.isNew()) {
            repository.save(current);
            answer.setText(String.format(UNREG, user.getFirstName()));
        } else if (!current.isEnabled()) {
            current.setEnabled(true);
            repository.save(current);
            answer.setText(String.format(STOPPED, user.getFirstName()));
        } else answer.setText(String.format(REG, user.getFirstName()));

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
        }
    }
}
