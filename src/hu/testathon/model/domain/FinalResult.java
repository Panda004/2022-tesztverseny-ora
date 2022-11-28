package hu.testathon.model.domain;

public class FinalResult {

    private final String id;
    private final int score;
    private final int order;

    public FinalResult(String id, int score) {
        this.id = id;
        this.score = score;
        this.order = 0;
    }

    public FinalResult(FinalResult result, int order) {
        this.id = result.id;
        this.score = result.score;
        this.order = order;
    }

    public Integer getScore() {
        return score;
    }

    public int getOrder() {
        return order;
    }

    public String printOrder(){
        return String.format("%d.díj (%d pont): %s", order, score, id);

    }

    @Override
    public String toString() {
        return id + " " + score;

    }
}
