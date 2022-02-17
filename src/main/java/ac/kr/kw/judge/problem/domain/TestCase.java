package ac.kr.kw.judge.problem.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class TestCase {
    @Column
    private String inputFilePath;
    @Column
    private String outputFilePath;
    @Column
    private String outputHash;

    protected TestCase() {
    }

    private TestCase(String inputFilePath, String outputFilePath, String outputHash) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.outputHash = outputHash;
    }

    public static TestCase of(String inputFilePath, String outputFilePath, String outputHash) {
        return new TestCase(inputFilePath, outputFilePath, outputHash);
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public String getOutputHash() {
        return outputHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return Objects.equals(inputFilePath, testCase.inputFilePath) && Objects.equals(outputFilePath, testCase.outputFilePath) && Objects.equals(outputHash, testCase.outputHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputFilePath, outputFilePath, outputHash);
    }
}
