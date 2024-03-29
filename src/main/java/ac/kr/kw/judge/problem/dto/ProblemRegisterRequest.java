package ac.kr.kw.judge.problem.dto;

import ac.kr.kw.judge.problem.domain.Description;
import ac.kr.kw.judge.problem.domain.Limit;

public class ProblemRegisterRequest {
    private String name;
    private Description description;
    private Limit limit;
    private int score;

    public ProblemRegisterRequest(String name, Description description, Limit limit, int score) {
        this.name = name;
        this.description = description;
        this.limit = limit;
        this.score = score;
    }

    public ProblemRegisterRequest() {
    }

    public String getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public Limit getLimit() {
        return limit;
    }

    public int getScore() {
        return score;
    }
}
