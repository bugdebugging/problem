package ac.kr.kw.judge.problem.adapter.out.execute;

import ac.kr.kw.judge.problem.adapter.out.execute.exception.CompileErrorException;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.ExecuteErrorException;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.FileHashFailedException;
import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.domain.ProgrammingLanguage;
import ac.kr.kw.judge.problem.service.port.out.CodeExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class CodeExecutorImpl implements CodeExecutor {

    @Override
    public boolean compileCode(ProgrammingLanguage programmingLanguage, File workDir) {
        ProcessBuilder compileProcessBuilder = new ProcessBuilder(programmingLanguage.getCompileOrder());
        compileProcessBuilder.directory(workDir);

        try {
            return executeProcess(compileProcessBuilder) == 0;
        } catch (IOException | InterruptedException exception) {
            throw new CompileErrorException(exception.getMessage());
        }
    }

    @Override
    public int executeCompiledCode(ProgrammingLanguage programmingLanguage, File workDir, File inputFile, Limit limit) {
        File errorFile = new File(workDir, errorFileName);
        File outputFile = new File(workDir, outputFileName);

        String[] commands=programmingLanguage.getExecuteOrder();
        commands[1]=Integer.toString(limit.getTime());

        ProcessBuilder executionProcessBuilder = new ProcessBuilder(commands);
        executionProcessBuilder.directory(workDir);
        executionProcessBuilder.redirectInput(inputFile);
        executionProcessBuilder.redirectOutput(outputFile);
        executionProcessBuilder.redirectError(errorFile);

        try {
            return executeProcess(executionProcessBuilder);
        } catch (IOException | InterruptedException exception) {
            throw new ExecuteErrorException(exception.getMessage());
        }
    }

    @Override
    public String clearWithHash(File workDir) {
        File errorFile = new File(workDir, errorFileName);
        File outputFile = new File(workDir, outputFileName);

        try(FileInputStream fis=new FileInputStream(outputFile);) {
            String result= Base64.getEncoder().encodeToString(DigestUtils.md5Digest(fis));
            errorFile.delete();
            outputFile.delete();
            return result;
        } catch (IOException e) {
            throw new FileHashFailedException(e.getMessage());
        }
    }

    private int executeProcess(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        Process process = processBuilder.start();
        process.waitFor();
        process.destroy();
        return process.exitValue();
    }
}
