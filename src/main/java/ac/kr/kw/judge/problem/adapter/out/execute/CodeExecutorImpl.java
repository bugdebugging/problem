package ac.kr.kw.judge.problem.adapter.out.execute;

import ac.kr.kw.judge.problem.adapter.out.execute.exception.FileHashFailedException;
import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.CompileErrorException;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.ExecuteErrorException;
import ac.kr.kw.judge.problem.service.port.out.CodeExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class CodeExecutorImpl implements CodeExecutor {

    @Override
    public boolean compileCode(File workDir) {
        ProcessBuilder compileProcessBuilder = new ProcessBuilder("javac", "MyApp.java");
        compileProcessBuilder.directory(workDir);

        try {
            return executeProcess(compileProcessBuilder);
        } catch (IOException | InterruptedException exception) {
            throw new CompileErrorException(exception.getMessage());
        }
    }

    @Override
    public boolean executeCompiledCode(File sourceDir, File inputFile, Limit limit) {
        File errorFile = new File(sourceDir, errorFileName);
        File outputFile = new File(sourceDir, outputFileName);

        String[] commands = {"timeout", Integer.toString(limit.getTime()), "java", "-Djava.security.manager", "-cp", ".", "MyApp"};
        ProcessBuilder executionProcessBuilder = new ProcessBuilder(commands);
        executionProcessBuilder.directory(sourceDir);
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

        FileInputStream fis = null;
        String result = "";
        try {
            fis = new FileInputStream(outputFile);
            result = DigestUtils.md5Digest(fis).toString();
        } catch (IOException e) {
            throw new FileHashFailedException(e.getMessage());
        }
        errorFile.delete();
        outputFile.delete();

        return result;
    }

    private boolean executeProcess(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        Process process = processBuilder.start();
        process.waitFor();
        process.destroy();
        if (process.exitValue() != 0)
            return false;
        return true;
    }
}