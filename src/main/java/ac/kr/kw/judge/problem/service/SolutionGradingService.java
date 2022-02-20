package ac.kr.kw.judge.problem.service;

import ac.kr.kw.judge.problem.domain.Problem;
import ac.kr.kw.judge.problem.domain.TestCase;
import ac.kr.kw.judge.problem.dto.GradingResult;
import ac.kr.kw.judge.problem.dto.Submit;
import ac.kr.kw.judge.problem.repository.ProblemRepository;
import ac.kr.kw.judge.problem.service.port.out.CodeExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolutionGradingService {
    private final ProblemRepository problemRepository;
    private final CodeExecutor codeExecutor;

    @Transactional(readOnly = true)
    public GradingResult gradeSolution(Long problemId, Submit submit) {
        Problem problem = problemRepository.findByIdWithTestCases(problemId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("해당 id의 problem이 존재하지 않습니다.");
                });
        File rootDir = new File("//execute//" + UUID.randomUUID().toString());
        rootDir.mkdir();

        makeSourceCodeFile(rootDir, submit);
        if(!codeExecutor.compileCode(rootDir)){
            return GradingResult.COMPILE_ERROR;
        }

        for (TestCase testCase : problem.getTestCases()) {
            if(!codeExecutor.executeCompiledCode(rootDir, new File(testCase.getInputFilePath()), problem.getLimit())){
                return GradingResult.RUNTIME_ERROR;
            }
            String hash = codeExecutor.clearWithHash(rootDir);
            if (!hash.equals(testCase.getOutputHash()))
                return GradingResult.FAILED;
        }
        return GradingResult.SUCCESS;
    }

    private void makeSourceCodeFile(File workDir, Submit submit) {
        File sourceCode = new File(workDir, "MyApp.java");
        try {
            sourceCode.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream(sourceCode)) {
            fos.write(submit.getSourceCode().getBytes());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}