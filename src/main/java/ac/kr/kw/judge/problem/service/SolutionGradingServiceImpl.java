package ac.kr.kw.judge.problem.service;

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
import java.io.FileOutputStream;
import java.io.IOException;
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

        Problem problem = problemRepository.findByIdWithTestCases(problemId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("해당 id의 problem이 존재하지 않습니다.");
                });

        File rootDir = new File("//execute//" + UUID.randomUUID().toString());
        rootDir.mkdir();

        ProgrammingLanguage programmingLanguage = ProgrammingLanguage.valueOf(submit.getProgrammingLanguage());
        makeSourceCodeFile(rootDir, submit, programmingLanguage);
        if (!codeExecutor.compileCode(programmingLanguage, rootDir)) {
            return GradingResult.COMPILE_ERROR;
        }

        for (TestCase testCase : problem.getTestCases()) {
            int exitValue = codeExecutor.executeCompiledCode(programmingLanguage, rootDir, new File(testCase.getInputFilePath()), problem.getLimit());
            if (exitValue == 1)
                return GradingResult.RUNTIME_ERROR;
            else if (exitValue == 124)
                return GradingResult.TIME_LIMIT;
            if (!codeExecutor.clearWithHash(rootDir).equals(testCase.getOutputHash()))
                return GradingResult.FAILED;
        }
        return GradingResult.SUCCESS;
    }

    private void makeSourceCodeFile(File workDir, Submit submit, ProgrammingLanguage programmingLanguage) {
        File sourceCode = new File(workDir, programmingLanguage.getFileName());
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
