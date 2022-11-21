package hu.testathon.controller;

import hu.testathon.model.domain.TestResult;
import hu.testathon.model.domain.TestValidator;

import java.util.List;

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
        return String.format("A feladatra 111 fő, a verenyzők %.2f%% adott helyes válaszat.",
                count, percent);
    }

    private long countCorrectAnswers(int taskNumber){
        return testResults.stream()
                .map(TestResult::getAnswers)
                .filter(i -> testValidator.isCorrect(i, taskNumber))
                .count();
    }


}
