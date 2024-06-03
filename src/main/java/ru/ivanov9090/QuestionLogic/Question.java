package ru.ivanov9090.QuestionLogic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Question {
    String text;
    String answer;
    String comment = "";
    String source = "";
    String author = "";

    public Question(String description) {
        int startQuestionAnswerIndex = description.indexOf("Ответ: ");
        if(description.indexOf("Автор: ", startQuestionAnswerIndex)>0){
            author = description.substring(description.indexOf("Автор: ", startQuestionAnswerIndex));
            description = description.replace(author, "");
        }
        if(description.indexOf("Источник(и): ", startQuestionAnswerIndex)>0){
            source = description.substring(description.indexOf("Источник(и): ", startQuestionAnswerIndex));
            description = description.replace(source, "");
        }
        if(description.indexOf("Комментарий: ", startQuestionAnswerIndex)>0){
            comment = description.substring(description.indexOf("Комментарий: ", startQuestionAnswerIndex));
            description = description.replace(comment, "");
        }
        text = description.substring(0, startQuestionAnswerIndex - 5);
        answer = description.substring(startQuestionAnswerIndex);
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public String getComment() {
        return comment;
    }

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }
}
