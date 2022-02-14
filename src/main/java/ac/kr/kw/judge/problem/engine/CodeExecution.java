package ac.kr.kw.judge.problem.engine;

import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.dto.Submit;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CodeExecution {
    public static void run(Limit limit, Submit submit) {
        String name = UUID.randomUUID().toString();

        File workDir = new File("D:\\intellij-workspace", name);
        workDir.mkdirs();

        File sourceCode = new File(workDir, "Main.java");
        File errorFile=new File(workDir,"error.txt");
        File outputFile=new File(workDir,"output.txt");

        try (FileWriter fw = new FileWriter(sourceCode);) {
            fw.write(submit.getSourceCode());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        //compile
        ProcessBuilder compileProcessBuilder = new ProcessBuilder("javac", "Main.java");
        compileProcessBuilder.directory(workDir);
        try {
            Process compileProcess = compileProcessBuilder.start();
            compileProcess.waitFor();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        //run
        ProcessBuilder executionProcessBuilder = new ProcessBuilder("java", "Main");
        executionProcessBuilder.directory(workDir);
        executionProcessBuilder.redirectError(errorFile);
        executionProcessBuilder.redirectError(outputFile);

        try {
            Process executionProcess = executionProcessBuilder.start();
            executionProcess = executionProcessBuilder.start();
            executionProcess.waitFor(limit.getTime(), TimeUnit.SECONDS);
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
