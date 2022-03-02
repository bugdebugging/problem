package ac.kr.kw.judge.problem.service;

import ac.kr.kw.judge.commons.utils.ProblemFileManager;
import ac.kr.kw.judge.problem.domain.Problem;
import ac.kr.kw.judge.problem.domain.ProgrammingLanguage;
import ac.kr.kw.judge.problem.domain.TestCase;
import ac.kr.kw.judge.problem.dto.GradingResult;
import ac.kr.kw.judge.problem.dto.Submit;
import ac.kr.kw.judge.problem.repository.ProblemRepository;
import ac.kr.kw.judge.problem.service.port.in.SolutionGradingService;
import ac.kr.kw.judge.problem.service.port.out.CodeExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolutionGradingServiceImpl implements SolutionGradingService {
    private final ProblemRepository problemRepository;
    private final CodeExecutor codeExecutor;

    @Transactional
    @Override
    public GradingResult gradeSolution(Long problemId, Submit submit) {
        ProgrammingLanguage.checkLanguageIsSupported(submit.getProgrammingLanguage());

        File rootDir = new File("//execute//" + UUID.randomUUID().toString());
        rootDir.mkdir();
        ProblemFileManager.createSourceCodeFile(rootDir, submit.getSourceCode(), ProgrammingLanguage.valueOf(submit.getProgrammingLanguage()));

        Problem problem = problemRepository.findByIdWithTestCases(problemId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("해당 id의 problem이 존재하지 않습니다.");
                });

        if (!codeExecutor.compileCode(ProgrammingLanguage.valueOf(submit.getProgrammingLanguage()), rootDir)) {
            return GradingResult.COMPILE_ERROR;
        }

        for (TestCase testCase : problem.getTestCases()) {
            int exitValue = codeExecutor.executeCompiledCode(ProgrammingLanguage.valueOf(submit.getProgrammingLanguage()), rootDir,
                    new File(testCase.getInputFilePath()), problem.getLimit());
            if (exitValue == 1)
                return GradingResult.RUNTIME_ERROR;
            else if (exitValue == 124)
                return GradingResult.TIME_LIMIT;
            if (!codeExecutor.clearWithHash(rootDir).equals(testCase.getOutputHash()))
                return GradingResult.FAILED;
        }
        return GradingResult.SUCCESS;
    }
}
