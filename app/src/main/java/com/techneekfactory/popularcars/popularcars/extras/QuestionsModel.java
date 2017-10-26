package com.techneekfactory.popularcars.popularcars.extras;

/**
 * Created by root on 10/23/17.
 */

public class QuestionsModel {

    String question;
    String answer;

    public QuestionsModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
