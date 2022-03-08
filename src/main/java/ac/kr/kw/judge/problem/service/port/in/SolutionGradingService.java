package ac.kr.kw.judge.problem.service.port.in;

import ac.kr.kw.judge.problem.domain.event.ChallengeSubmitted;
import ac.kr.kw.judge.problem.domain.event.GradingResult;
import ac.kr.kw.judge.problem.dto.Submit;

public interface SolutionGradingService {
    GradingResult gradeSolution(Long problemId, Submit submit);

    void gradeChallengeSubmit(Long problemId, ChallengeSubmitted challengeSubmitted);
}
