package hu.testathon;

import hu.testathon.controller.TestService;
import hu.testathon.model.domain.TestValidator;
import hu.testathon.model.service.*;

import java.util.Scanner;

public class App {

    private final TestService testService;
    private final Console console;
    private final FileWriter fileWriter;

    private App() {
        console = new Console(new Scanner(System.in));
        fileWriter = new FileWriter("pontok.txt");
        DataApi dataApi = new DataApi("valaszok.txt",
                new FileReader(), new DataParser());
        testService = new TestService(dataApi.getTestResults(), new TestValidator(dataApi.getAnswers()));
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("1. feladat: Az adatok beolvasása");
        System.out.println("2.feladat: A vetéledőn " + testService.getCompetitorsCount() + " versenyző indult.");
        System.out.print("3. feladat: A versenyző azonosítója = ");
        String id = console.read();
        System.out.println(testService.getAnswerById(id) + "\t(a versenyző válasza)");
        System.out.println("4. feladat:");
        System.out.println(testService.getCheckedResult(id));
        System.out.print("5. feladat: A feladat sorszáma = ");
        int taskNumber = console.readInt();
        System.out.println(testService.getCorrectAnswerStatistic(taskNumber));
    }
}
