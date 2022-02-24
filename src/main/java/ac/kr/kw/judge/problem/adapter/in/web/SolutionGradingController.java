package ac.kr.kw.judge.problem.adapter.in.web;

import ac.kr.kw.judge.problem.dto.GradingResult;
import ac.kr.kw.judge.problem.dto.Submit;
import ac.kr.kw.judge.problem.service.port.in.SolutionGradingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"problems"})
@RestController
@RequiredArgsConstructor
public class SolutionGradingController {
    private final SolutionGradingService solutionGradingService;

    @ApiOperation(value = "소스코드 제출", notes = "문제에 대한 솔루션 코드 제출")
    @PostMapping("/api/problems/{problemId}/grading")
    public GradingResult gradingSolution(@PathVariable("problemId")Long problemId, @RequestBody Submit submit){
        return solutionGradingService.gradeSolution(problemId,submit);
    }
}
