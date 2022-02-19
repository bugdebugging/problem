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

    private static int MIN_NUM_TESTCASE = 1;

    public ProblemRegisterCommand(String name, Description description, Limit limit, List<TestCase> testCase) {
        checkArgument(isNotEmpty(name), "문제의 name은 필수입니다.");
        checkArgument(description != null, "문제의 description은 필수입니다.");
        checkArgument(limit != null, "문제의 limit은 필수입니다.");

        checkArgument(testCase != null, "문제의 TestCase는 필수입니다.");
        checkArgument(testCase.size() >= MIN_NUM_TESTCASE, "문제의 TestCase는 적어도 하나 이상 어어야합니다");

        this.name = name;
        this.description = description;
        this.limit = limit;
        this.testCase = testCase;
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
}
