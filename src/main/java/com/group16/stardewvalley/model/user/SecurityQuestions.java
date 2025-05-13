package com.group16.stardewvalley.model.user;

public enum SecurityQuestions {
    q1("What was the name of your imaginary friend?"),
    q2("If you were a cartoon character, who would you be?"),
    q3("If you were a fruit, what fruit would you be?"),
    q4("If you had a pet dragon, what would you name it?"),
    q5("What sandwich would you fight a raccoon over?");

    final String question;

    SecurityQuestions(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}