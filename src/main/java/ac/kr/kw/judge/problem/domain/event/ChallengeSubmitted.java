package ac.kr.kw.judge.problem.domain.event;

public class ChallengeSubmitted {
    private Long problemId;
    private String username;
    private Long challengeId;
    private Long submitId;
    private String programmingLanguage;
    private String sourceCode;

    public ChallengeSubmitted() {
    }

    public Long getProblemId() {
        return problemId;
    }

    public String getUsername() {
        return username;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public Long getSubmitId() {
        return submitId;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public String getSourceCode() {
        return sourceCode;
    }
}
