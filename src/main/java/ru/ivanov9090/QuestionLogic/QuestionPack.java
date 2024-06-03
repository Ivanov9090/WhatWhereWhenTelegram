package ru.ivanov9090.QuestionLogic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionPack {
    private List<Question> questions = new ArrayList<>();

    public QuestionPack() {
        try {
            Document document = Jsoup.connect("https://db.chgk.info/random").get();
            String all = document.getElementsByClass("random_question").text();
            for (int i = 1; i <= 24; i++) {
                int startDescription = all.indexOf("Вопрос " + i + ": ");
                int endDescription = i != 24 ? all.indexOf("Вопрос " + (i + 1) + ": ") : all.length();
                String description = all.substring(startDescription, endDescription);
                questions.add(new Question(description));
            }
        } catch (IOException e) {
            System.out.println("Проблема с сайтом https://db.chgk.info/");
        }
    }

    public Question getQuestion(int number) {
        return questions.get(number);
    }
}
