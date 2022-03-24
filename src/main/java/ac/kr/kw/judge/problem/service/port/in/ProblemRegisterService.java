package ac.kr.kw.judge.problem.service.port.in;

import ac.kr.kw.judge.problem.service.command.ProblemRegisterCommand;

public interface ProblemRegisterService {
    Long registerNewProblem(String username, ProblemRegisterCommand problemRegisterCommand);
}
