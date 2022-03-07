package ac.kr.kw.judge.problem.dto;

import ac.kr.kw.judge.problem.domain.Description;
import ac.kr.kw.judge.problem.domain.Limit;

public class ProblemModifyRequest {
    private Description description;
    private Limit limit;
    private String name;
    private int score;

    public ProblemModifyRequest() {
    }

    public Description getDescription() {
        return description;
    }

    public Limit getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
