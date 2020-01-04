package ru.drundushka.tgbot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drundushka.tgbot.model.Profile;
import ru.drundushka.tgbot.repository.ProfileRepository;

@Component
public class StopCommand extends BotCommand {

    public static final String UNREG = "Привет %s! Вы еще не зарегистрированы!";
    public static final String STOP = "Профиль деактивирован! До свидания, %s";
    public static final String STOPPED = "Ваш профиль уже деактивирован";

    private final ProfileRepository repository;

    public StopCommand(ProfileRepository repository) {
        super("stop", "Stop bot an disable current user");
        this.repository = repository;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());

        Profile current = repository.findByTgId(user.getId())
                .orElse(new Profile(null,
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUserName(),
                        true,
                        0));

        if (current.isNew()) {
            answer.setText(String.format(UNREG, user.getFirstName()));
        } else if (!current.isEnabled()) {
            answer.setText(String.format(STOPPED, user.getFirstName()));
        } else {
            current.setEnabled(false);
            repository.save(current);
            answer.setText(String.format(STOP, user.getFirstName()));
        }
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
        }
    }
}
