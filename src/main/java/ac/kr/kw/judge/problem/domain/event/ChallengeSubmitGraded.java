package ac.kr.kw.judge.problem.domain.event;

public class ChallengeSubmitGraded {
    private GradingResult result;
    private String username;
    private Long challengeId;
    private Long submitId;

    private ChallengeSubmitGraded(GradingResult result, String username, Long challengeId, Long submitId) {
        this.result = result;
        this.username = username;
        this.challengeId = challengeId;
        this.submitId = submitId;
    }

    public static ChallengeSubmitGraded of(GradingResult result, String username, Long challengeId, Long submitId) {
        return new ChallengeSubmitGraded(result, username, challengeId, submitId);
    }

    public GradingResult getResult() {
        return result;
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
}
