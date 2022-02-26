package ac.kr.kw.judge.problem.service.command;

import ac.kr.kw.judge.problem.domain.Description;
import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.domain.TestCase;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class ProblemRegisterCommand {
    private String name;
    private Description description;
    private Limit limit;
    private List<TestCase> testCase;
    private int score;

    private static int MIN_NUM_TESTCASE = 1;

    public ProblemRegisterCommand(String name, Description description, Limit limit, List<TestCase> testCase, int score) {
        checkArgument(isNotEmpty(name), "문제의 name은 필수입니다.");
        checkArgument(description != null, "문제의 description은 필수입니다.");
        checkArgument(limit != null, "문제의 limit은 필수입니다.");

        checkArgument(testCase != null, "문제의 TestCase는 필수입니다.");
        checkArgument(testCase.size() >= MIN_NUM_TESTCASE, "문제의 TestCase는 적어도 하나 이상 어어야합니다");

        checkArgument(score >= 0 && score < 2500, "문제의 점수 범위는 0~2499입니다.");

        this.name = name;
        this.description = description;
        this.limit = limit;
        this.testCase = testCase;
        this.score = score;
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

    public List<TestCase> getTestCase() {
        return testCase;
    }

    public int getScore() {
        return score;
    }
}
