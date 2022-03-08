package ac.kr.kw.judge.problem.domain.event;

public class ChallengeSubmitted {
    private Long problemId;
    private Long participationId;
    private Long submitId;
    private String programmingLanguage;
    private String sourceCode;

    public ChallengeSubmitted() {
    }

    public Long getProblemId() {
        return problemId;
    }

    public Long getParticipationId() {
        return participationId;
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
