package ac.kr.kw.judge.problem.service;

import ac.kr.kw.judge.commons.exception.UnAuthorizedException;
import ac.kr.kw.judge.problem.domain.Problem;
import ac.kr.kw.judge.problem.domain.event.ProblemChanged;
import ac.kr.kw.judge.problem.repository.ProblemRepository;
import ac.kr.kw.judge.problem.service.command.ProblemModifyCommand;
import ac.kr.kw.judge.problem.service.port.in.ProblemModifyService;
import ac.kr.kw.judge.problem.service.port.out.EventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemModifyServiceImpl implements ProblemModifyService {
    private final ProblemRepository problemRepository;
    private final EventSender eventSender;

    @Override
    public void modifyProblemInfo(String username, ProblemModifyCommand problemModifyCommand) {
        Problem problem = problemRepository.findById(problemModifyCommand.getProblemId())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("해당 id의 문제가 존재하지 않습니다.");
                });
        if (!problem.getAuthor().equals(username)) {
            throw new UnAuthorizedException("해당 문제의 저자가 아닙니다.");
        }

        problem.changeInfo(problemModifyCommand.getDescription()
                , problemModifyCommand.getLimit()
                , problemModifyCommand.getName()
                , problemModifyCommand.getScore());

        eventSender.publish("problem_changed", ProblemChanged.fromEntity(problem));
    }
}
