package ac.kr.kw.judge.problem.adapter.in.web;

import ac.kr.kw.judge.commons.apis.ApiResult;
import ac.kr.kw.judge.commons.apis.ApiUtils;
import ac.kr.kw.judge.problem.dto.ProblemModifyRequest;
import ac.kr.kw.judge.problem.service.command.ProblemModifyCommand;
import ac.kr.kw.judge.problem.service.port.in.ProblemModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemModifyController {
    private final ProblemModifyService problemModifyService;

    @PutMapping("/api/problems/{problemId}")
    public ApiResult modifyProblemInfo(@PathVariable("problemId") Long problemId, @RequestBody ProblemModifyRequest problemModifyRequest) {
        ProblemModifyCommand problemModifyCommand = new ProblemModifyCommand(problemId
                , problemModifyRequest.getDescription()
                , problemModifyRequest.getLimit()
                , problemModifyRequest.getName()
                , problemModifyRequest.getScore());
        problemModifyService.modifyProblemInfo(problemModifyCommand);
        return ApiUtils.success("successfully modify problem info");
    }
}
