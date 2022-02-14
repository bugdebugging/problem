package ac.kr.kw.judge.problem.dto;

public class Submit {
    private String sourceCode;
    private String programmingLanguage;

    private Submit(String sourceCode, String programmingLanguage) {
        this.sourceCode = sourceCode;
        this.programmingLanguage = programmingLanguage;
    }

    public static Submit of(String sourceCode, String programmingLanguage) {
        return new Submit(sourceCode, programmingLanguage);
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }
}
