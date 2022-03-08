package ac.kr.kw.judge.problem.domain.event;

public class ChallengeSubmitGraded {
    private GradingResult result;
    private Long participationId;
    private Long submitId;

    private ChallengeSubmitGraded(GradingResult result, Long participationId, Long submitId) {
        this.result = result;
        this.participationId = participationId;
        this.submitId = submitId;
    }

    public static ChallengeSubmitGraded of(GradingResult result, Long participationId, Long submitId) {
        return new ChallengeSubmitGraded(result, participationId, submitId);
    }

    public GradingResult getResult() {
        return result;
    }

    public Long getParticipationId() {
        return participationId;
    }

    public Long getSubmitId() {
        return submitId;
    }
}
