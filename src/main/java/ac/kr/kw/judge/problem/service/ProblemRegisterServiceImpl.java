package ac.kr.kw.judge.problem.service;

import ac.kr.kw.judge.problem.domain.Problem;
import ac.kr.kw.judge.problem.domain.event.ProblemCreated;
import ac.kr.kw.judge.problem.repository.ProblemRepository;
import ac.kr.kw.judge.problem.service.command.ProblemRegisterCommand;
import ac.kr.kw.judge.problem.service.port.in.ProblemRegisterService;
import ac.kr.kw.judge.problem.service.port.out.EventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemRegisterServiceImpl implements ProblemRegisterService {
    private final ProblemRepository problemRepository;
    private final EventSender eventSender;

    @Override
    public Long registerNewProblem(String username, ProblemRegisterCommand problemRegisterCommand) {
        Problem problem = new Problem(problemRegisterCommand.getName(),
                problemRegisterCommand.getDescription(),
                problemRegisterCommand.getLimit(),
                problemRegisterCommand.getTestCase(),
                problemRegisterCommand.getScore()
                , username);
        problemRepository.save(problem);

        eventSender.publish("problem_created", ProblemCreated.fromEntity(problem));
        return problem.getId();
    }
}
