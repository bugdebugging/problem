package ac.kr.kw.judge.execute;

import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.service.port.out.CodeExecutor;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CodeExecutorTest {
    @Autowired
    CodeExecutor codeExecutor;

    final File testDir = new File("//test");
    File sourceCode, inputFile;

    @BeforeEach
    void setUp() throws IOException {
        sourceCode = new File(testDir, "MyApp.java");
        sourceCode.createNewFile();

        inputFile = new File(inputFile, "input.txt");
        FileOutputStream fos = new FileOutputStream(inputFile);
        fos.write("5".getBytes());
        fos.close();
    }

    @AfterEach
    void tearUp() {
        if (sourceCode.exists()) {
            sourceCode.delete();
        }
        if (inputFile.exists()) {
            inputFile.delete();
        }
    }

    @Test
    @DisplayName("컴파일 에러나는 코드실행")
    void 컴파일_에러나는_코드실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(sourceCode);
        String willFailedAtCompile = "import java.util.Scanner;\n" +
                "\n" +
                "public class MyApp {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner sc=new Scanner(System.in);\n" +
                "        int n=sc.nextInt();\n" +
                "        for(int i=0; i<n; i++){\n" +
                "            .out.println(\"hello world~\");\n" +
                "        }\n" +
                "    }\n" +
                "}";
        fos.write(willFailedAtCompile.getBytes());
        fos.close();
        assertFalse(codeExecutor.compileCode(testDir));
    }

    @Test
    @DisplayName("런타임 에러나는 코드실행")
    void 런타임_에러나는_코드실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(sourceCode);
        String willFailedAtCompile = "import java.util.Scanner;\n" +
                "\n" +
                "public class MyApp {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner sc=new Scanner(System.in);\n" +
                "        int n=sc.nextInt();\n" +
                "        for(int i=0; i<n; i++){\n" +
                "            System.out.println(\"hello world~\");\n" +
                "        }\n" +
                "        System.out.println(n/0);\n" +
                "    }\n" +
                "}";
        fos.write(willFailedAtCompile.getBytes());
        fos.close();
        codeExecutor.compileCode(testDir);
        assertFalse(codeExecutor.executeCompiledCode(testDir, inputFile, Limit.of(512, 2)));
    }

    @Test
    @DisplayName("정상적인 코드실행")
    void 정상적_코드실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(sourceCode);
        String willFailedAtCompile = "import java.util.Scanner;\n" +
                "\n" +
                "public class MyApp {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner sc=new Scanner(System.in);\n" +
                "        int n=sc.nextInt();\n" +
                "        for(int i=0; i<n; i++){\n" +
                "            System.out.println(\"hello world~\");\n" +
                "        }\n" +
                "    }\n" +
                "}";
        fos.write(willFailedAtCompile.getBytes());
        fos.close();
        codeExecutor.compileCode(testDir);
        assertTrue(codeExecutor.executeCompiledCode(testDir, inputFile, Limit.of(512, 2)));
    }

    //시간 초과
    //위험한 code(File,Net) run
}
