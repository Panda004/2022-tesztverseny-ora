package hu.testathon.controller;

import hu.testathon.model.domain.FinalResult;
import hu.testathon.model.domain.TestResult;
import hu.testathon.model.domain.TestValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestService {

    private final List<TestResult> testResults;
    private final TestValidator testValidator;

    public TestService(List<TestResult> testResults, TestValidator testValidator) {
        this.testResults = testResults;
        this.testValidator = testValidator;
    }

    public int getCompetitorsCount(){
        return testResults.size();
    }

    public  String getAnswerById(String id){
        return getTestResultById(id).getAnswers();
    }

    private TestResult getTestResultById(String id){
        return testResults.stream()
                .filter(i -> i.isCompetitor(id))
                .findAny()
                .get();
    }

    public String getCheckedResult(String id){
        return String.format("%s\t(a helyes megoldás)\n" +
                "%s\t(A versenyző helyes válaszai)",
                testValidator.getAnswer(),
                testValidator.checkResults(getAnswerById(id)));
    }

    public String getCorrectAnswerStatistic(int taskNumber) {
        long count = countCorrectAnswers(taskNumber - 1);
        double percent = count * 100.0 / getCompetitorsCount();
        return String.format("A feladatra %d fő, a verenyzők %.2f%% adott helyes válaszat.",
                count, percent);
    }

    private long countCorrectAnswers(int taskNumber){
        return testResults.stream()
                .map(TestResult::getAnswers)
                .filter(i -> testValidator.isCorrect(i, taskNumber))
                .count();
    }

    public List<String> getScores(){
        return createFinalResult().stream()
                .map(FinalResult::toString)
                .collect(Collectors.toList());
    }

    private List<FinalResult> createFinalResult(){
        return testResults.stream()
                .map(this::createFinalResult)
                .collect(Collectors.toList());
    }

    private FinalResult createFinalResult(TestResult testResult){
        int score = testValidator.calculateScore(testResult.getAnswers());
        return new FinalResult(testResult.getId(), score);
    }

   public String getOrderedResults(){
        return createOrderedFinalResults().stream()
                .filter(i -> i.getOrder() <= 3)
                .map(FinalResult::printOrder)
                .collect(Collectors.joining("\n"));
   }


    private List<FinalResult> createOrderedFinalResults(){
        List<FinalResult> sortedFinalResults = createSortedFinalResult();
        List<FinalResult> orderedFinalResults = new ArrayList<>();
        int prevOrder = 0, prevScore = 0;
        for (FinalResult finalResult: sortedFinalResults) {
            int order = finalResult.getScore() == prevScore
                ? prevOrder : prevOrder + 1;
            orderedFinalResults.add(new FinalResult(finalResult, order));
            prevOrder = order;
            prevScore = finalResult.getScore();
        }
        return orderedFinalResults;


   }


    public List<FinalResult> createSortedFinalResult(){
        return createFinalResult().stream()
                .sorted((i, j) -> j.getScore().compareTo(i.getScore()))
                .collect(Collectors.toList());
    }

}
