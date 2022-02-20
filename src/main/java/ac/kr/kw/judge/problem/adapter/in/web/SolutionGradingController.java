package ac.kr.kw.judge.problem.adapter.in.web;

import ac.kr.kw.judge.problem.dto.GradingResult;
import ac.kr.kw.judge.problem.dto.Submit;
import ac.kr.kw.judge.problem.service.SolutionGradingServiceImpl;
import ac.kr.kw.judge.problem.service.port.in.SolutionGradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SolutionGradingController {
    private final SolutionGradingService solutionGradingService;

    @PostMapping("/api/problems/{problemId}/grading")
    public GradingResult gradingSolution(@PathVariable("problemId")Long problemId, @RequestBody Submit submit){
        return solutionGradingService.gradeSolution(problemId,submit);
    }
}
