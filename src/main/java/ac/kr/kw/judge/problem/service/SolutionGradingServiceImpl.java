package ac.kr.kw.judge.problem.service;

import ac.kr.kw.judge.commons.utils.ProblemFileManager;
import ac.kr.kw.judge.problem.domain.Problem;
import ac.kr.kw.judge.problem.domain.ProgrammingLanguage;
import ac.kr.kw.judge.problem.domain.TestCase;
import ac.kr.kw.judge.problem.domain.event.ChallengeSubmitGraded;
import ac.kr.kw.judge.problem.domain.event.ChallengeSubmitted;
import ac.kr.kw.judge.problem.domain.event.GradingResult;
import ac.kr.kw.judge.problem.dto.GradingStatus;
import ac.kr.kw.judge.problem.dto.Submit;
import ac.kr.kw.judge.problem.repository.ProblemRepository;
import ac.kr.kw.judge.problem.service.port.in.SolutionGradingService;
import ac.kr.kw.judge.problem.service.port.out.CodeExecutor;
import ac.kr.kw.judge.problem.service.port.out.EventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@RequiredArgsConstructor
public class SolutionGradingServiceImpl implements SolutionGradingService {
    private final ProblemRepository problemRepository;
    private final CodeExecutor codeExecutor;
    private final EventSender eventSender;


    @Transactional
    @Override
    public GradingResult gradeSolution(Long problemId, Submit submit) {
        File rootDir = ProblemFileManager.createExecuteDir();
        ProblemFileManager.createSourceCodeFile(rootDir, submit.getSourceCode(), ProgrammingLanguage.ofSupportedLanguage(submit.getProgrammingLanguage()));

        Problem problem = problemRepository.findByIdWithTestCases(problemId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("해당 id의 problem이 존재하지 않습니다.");
                });

        if (!codeExecutor.compileCode(ProgrammingLanguage.ofSupportedLanguage(submit.getProgrammingLanguage()), rootDir)) {
            return GradingResult.of(GradingStatus.COMPILE_ERROR, 0);
        }
        return gradeProblemTestCases(problem, ProgrammingLanguage.ofSupportedLanguage(submit.getProgrammingLanguage()), rootDir);
    }

    @Override
    public void gradeChallengeSubmit(Long problemId, ChallengeSubmitted submit) {
        File rootDir = ProblemFileManager.createExecuteDir();
        ProblemFileManager.createSourceCodeFile(rootDir, submit.getSourceCode(), ProgrammingLanguage.ofSupportedLanguage(submit.getProgrammingLanguage()));

        Problem problem = problemRepository.findByIdWithTestCases(problemId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("해당 id의 problem이 존재하지 않습니다.");
                });

        ChallengeSubmitGraded challengeSubmitGraded = null;
        if (!codeExecutor.compileCode(ProgrammingLanguage.ofSupportedLanguage(submit.getProgrammingLanguage()), rootDir)) {
            GradingResult compileErrorResult = GradingResult.of(GradingStatus.COMPILE_ERROR, 0);
            challengeSubmitGraded = ChallengeSubmitGraded.of(compileErrorResult, submit.getUsername(), submit.getChallengeId(), submit.getSubmitId());
        } else {
            GradingResult result = gradeProblemTestCases(problem, ProgrammingLanguage.ofSupportedLanguage(submit.getProgrammingLanguage()), rootDir);
            challengeSubmitGraded = ChallengeSubmitGraded.of(result, submit.getUsername(), submit.getChallengeId(), submit.getSubmitId());
        }
        eventSender.publish("grade", challengeSubmitGraded);
    }

    private GradingResult gradeProblemTestCases(Problem problem, ProgrammingLanguage programmingLanguage, File rootDir) {
        GradingResult result = GradingResult.of(GradingStatus.SUCCESS, problem.getScore());
        for (TestCase testCase : problem.getTestCases()) {
            int exitValue = codeExecutor.executeCompiledCode(programmingLanguage, rootDir,
                    new File(testCase.getInputFilePath()), problem.getLimit());
            if (exitValue == 1) {
                result = GradingResult.of(GradingStatus.RUNTIME_ERROR, 0);
            } else if (exitValue == 124) {
                result = GradingResult.of(GradingStatus.TIME_LIMIT, 0);
            }
            if (!codeExecutor.clearWithHash(rootDir).equals(testCase.getOutputHash())) {
                result = GradingResult.of(GradingStatus.FAILED, 0);
            }
            if (result.getStatus() != GradingStatus.SUCCESS) {
                break;
            }
        }
        return result;
    }
}
