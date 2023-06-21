package ecs.components;

/** Data format for the questions the Riddleman asks the hero */
public record Questions(String question, String answer) {

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}
;
