package ac.kr.kw.judge.problem.service.port.out;

import ac.kr.kw.judge.problem.domain.Limit;

import java.io.File;

public interface CodeExecutor {
    String outputFileName = "output.txt";
    String errorFileName = "error.txt";

    boolean compileCode(File workDir);

    boolean executeCompiledCode(File workDir, File inputFile, Limit limit);

    String clearWithHash(File workDir);
}
