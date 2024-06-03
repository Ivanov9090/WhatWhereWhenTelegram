package ru.ivanov9090;

import org.apache.shiro.session.Session;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.session.TelegramLongPollingSessionBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ivanov9090.QuestionLogic.QuestionPack;

import java.util.Optional;

public class TelegramBot extends TelegramLongPollingSessionBot  {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private int nextQuestion = 0;
    QuestionPack questionPack;

    public TelegramBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;
        questionPack = new QuestionPack();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update, Optional<Session> optionalSession) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String answer = update.getMessage().getText();
            String[] splitAnswer = answer.split(" ");
            String action = splitAnswer[0];
            switch (action) {
                case "/help" :
                    break;
                case "/hello" :
                    sendMassage("Привет, я бот для игры в ЧГК", chatId);
                    break;
                case "/start":
                    sendMassage(questionPack.getQuestion(nextQuestion).getText(), chatId);
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sendMassage(questionPack.getQuestion(nextQuestion).getAnswer(), chatId);
                    sendMassage(questionPack.getQuestion(nextQuestion).getComment(), chatId);
                    nextQuestion = nextQuestion == 24 ? 0 : nextQuestion+1;
                    break;
                default: sendMassage("Ты по-моему перепутал", chatId);
            }
        }
    }

    public void sendMassage(String text, long chatId){
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            System.out.println("Что-то пошло не так, собщение не отправлено");
        }
    }
}
