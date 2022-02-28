package ac.kr.kw.judge.problem.service.port.out;

import ac.kr.kw.judge.problem.dto.ProblemDto;

public interface ProblemChangeEventSender {
    void publish(ProblemDto problemDto);
}
