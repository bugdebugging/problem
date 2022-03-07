package ac.kr.kw.judge.problem.service.port.in;

import ac.kr.kw.judge.problem.service.command.ProblemModifyCommand;

public interface ProblemModifyService {
    void modifyProblemInfo(ProblemModifyCommand problemModifyCommand);
}
