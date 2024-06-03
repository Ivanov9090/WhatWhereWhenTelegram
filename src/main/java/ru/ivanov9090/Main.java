package ru.ivanov9090;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ivanov9090.QuestionLogic.Question;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        new TelegramBot("TelegramBot", "");
    }
}
