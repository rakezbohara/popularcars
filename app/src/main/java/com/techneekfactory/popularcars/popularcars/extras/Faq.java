package com.techneekfactory.popularcars.popularcars.extras;

/**
 * Created by root on 10/20/17.
 */

public class Faq {

    String question, answer;

    public Faq(String question, String answer) {
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
