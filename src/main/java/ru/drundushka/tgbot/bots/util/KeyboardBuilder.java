package ru.drundushka.tgbot.bots.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardBuilder {

    private ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    private List<KeyboardRow> keyboard = new ArrayList<>();

    public KeyboardBuilder() {
        // init first row
        keyboard.add(new KeyboardRow());
    }

    public KeyboardBuilder nextRow() {
        keyboard.add(new KeyboardRow());
        return this;
    }

    public KeyboardBuilder addButton(String button) {
        keyboard.get(keyboard.size() - 1).add(button);
        return this;
    }

    public KeyboardBuilder setResizable(boolean resizable){
        markup.setResizeKeyboard(resizable);
        return this;
    }

    public ReplyKeyboardMarkup build() {
        return markup.setKeyboard(keyboard)
                .setOneTimeKeyboard(false)
                .setResizeKeyboard(true);
    }
}
