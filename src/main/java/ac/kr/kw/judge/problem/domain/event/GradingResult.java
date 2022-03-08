package ac.kr.kw.judge.problem.domain.event;

import ac.kr.kw.judge.problem.dto.GradingStatus;

public class GradingResult {
    private GradingStatus status;
    private int score;

    private GradingResult(GradingStatus status, int score) {
        this.status = status;
        this.score = score;
    }

    public static GradingResult of(GradingStatus status, int score) {
        return new GradingResult(status, score);
    }

    public GradingStatus getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }
}
