package ru.drundushka.tgbot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class CokesCommand extends BotCommand {

    private static final double CIG_COST = (115 + (107.25 + 143) / 2) / 2;
    private static final double STARTING_COSTS = 2990 + 1990 + 390 * 2 + 100;
    private static final double ZHIZH_COSTS = 500;
    private static final double TEMPED_COSTS = 650 + 200 + 270 + 690 + 200 + 200 + 250 + 250 + 100 + 300;

    public CokesCommand() {
        super("cokes", "shows Cokes' non-smoking time");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(getNonSmokingTime());

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
        }
    }

    private String getNonSmokingTime() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last = LocalDateTime.of(2018, Month.SEPTEMBER, 26, 0, 0, 0);

        long dayIndicator = ChronoUnit.DAYS.between(last, now) % 10;

        String dayWord = "дней";

        if (dayIndicator == 1)
            dayWord = "день";
        if (dayIndicator >= 2 && dayIndicator <= 4)
            dayWord = "дня";


        double noSmokingTime = ChronoUnit.HOURS.between(last, now) / 24D;
        double savedMoney = noSmokingTime * CIG_COST * 1.25; // Модификатор на 1.15 пачки в день
        double totalSavings = (savedMoney - STARTING_COSTS - TEMPED_COSTS - ZHIZH_COSTS * (ChronoUnit.MONTHS.between(last, now)));
        double daySavings = totalSavings / noSmokingTime;


        return String.format("\uD83D\uDEAD" + " Кокес не курит: %d %s%n" +
                        "Не выкурено сигарет ~ %.0f%n" +
                        "Экономия только на сигах ~ %.2f руб.%n" +
                        "Стартовые расходы ~ %.2f руб.%n" +
                        "Расходы на жыжу в месяц ~ %d руб./мес.%n" +
                        "Расходы на всякую поебень ~ %.2f руб.%n" +
                        "Экономия общая ~ %.2f руб.%n" +
                        "Экономия в день ~ %.2f руб.%n" +
                        "Экономия в час ~ %.2f руб.%n",

                ChronoUnit.DAYS.between(last, now),
                dayWord,
                noSmokingTime * 20D * 1.25D,
                savedMoney,
                STARTING_COSTS,
                Math.round(ZHIZH_COSTS),
                TEMPED_COSTS,
                totalSavings,
                daySavings,
                (daySavings / 24));
    }
}
