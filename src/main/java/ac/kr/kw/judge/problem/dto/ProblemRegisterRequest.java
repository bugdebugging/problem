package ac.kr.kw.judge.problem.dto;

import ac.kr.kw.judge.problem.domain.Description;
import ac.kr.kw.judge.problem.domain.Limit;

public class ProblemRegisterRequest {
    private String name;
    private Description description;
    private Limit limit;

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
}
