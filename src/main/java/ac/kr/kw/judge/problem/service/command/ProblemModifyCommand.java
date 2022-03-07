package ac.kr.kw.judge.problem.service.command;

import ac.kr.kw.judge.problem.domain.Description;
import ac.kr.kw.judge.problem.domain.Limit;

public class ProblemModifyCommand {
    private Long problemId;
    private Description description;
    private Limit limit;
    private String name;
    private int score;

    public ProblemModifyCommand(Long problemId, Description description, Limit limit, String name, int score) {
        this.problemId = problemId;
        this.description = description;
        this.limit = limit;
        this.name = name;
        this.score = score;
    }

    public Long getProblemId() {
        return problemId;
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
