package ac.kr.kw.judge.problem.service;

import ac.kr.kw.judge.problem.domain.Problem;
import ac.kr.kw.judge.problem.repository.ProblemRepository;
import ac.kr.kw.judge.problem.service.command.ProblemRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemRegisterService {
    private final ProblemRepository problemRepository;

    public Long registerNewProblem(ProblemRegisterCommand problemRegisterCommand) {
        Problem problem = new Problem(problemRegisterCommand.getName(),
                problemRegisterCommand.getDescription(),
                problemRegisterCommand.getLimit(),
                problemRegisterCommand.getTestCase());
        problemRepository.save(problem);
        return problem.getId();
    }
}
