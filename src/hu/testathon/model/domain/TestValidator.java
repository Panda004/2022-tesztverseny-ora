package hu.testathon.model.domain;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestValidator {

    private static final int[] POINTS = {3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 6};

    private final String answer;

    public TestValidator(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect(String competitorAnswer, int i) {
        return competitorAnswer.charAt(i) == answer.charAt(i);
    }

    public String checkResults(String competitorAnswer) {
        return IntStream.range(0, answer.length())
                .mapToObj(i -> checkAnswer(competitorAnswer, i))
                .collect(Collectors.joining());
    }

    private String checkAnswer(String competitorAnswer, int i) {
        return isCorrect(competitorAnswer, i) ? "+" : " ";
    }

    public int calculateScore(String competitorAnswer) {
        return IntStream.range(0, answer.length())
                .map(i -> getScore(competitorAnswer, i))
                .sum();
    }

    private int getScore(String competitorAnswer, int i){
        return isCorrect(competitorAnswer, i) ? POINTS[i] : 0;
    }

}




