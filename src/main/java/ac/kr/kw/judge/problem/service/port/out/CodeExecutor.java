package ac.kr.kw.judge.problem.service.port.out;

import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.domain.ProgrammingLanguage;

import java.io.File;

public interface CodeExecutor {
    String outputFileName = "output.txt";
    String errorFileName = "error.txt";

    boolean compileCode(ProgrammingLanguage programmingLanguage, File workDir);

    int executeCompiledCode(ProgrammingLanguage programmingLanguage,File workDir, File inputFile, Limit limit);

    String clearWithHash(File workDir);
}
