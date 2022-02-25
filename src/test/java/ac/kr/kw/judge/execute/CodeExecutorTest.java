package ac.kr.kw.judge.execute;

import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.domain.ProgrammingLanguage;
import ac.kr.kw.judge.problem.service.port.out.CodeExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CodeExecutorTest {
    @Autowired
    CodeExecutor codeExecutor;

    final File testDir = new File("//test");
    File cppSourceCodeFile, cSourceCodeFile, javaSourceCodeFile, inputFile;

    @BeforeEach
    void setUp() throws IOException {
        javaSourceCodeFile = new File(testDir, ProgrammingLanguage.JAVA.getFileName());
        javaSourceCodeFile.createNewFile();

        cppSourceCodeFile = new File(testDir, ProgrammingLanguage.CPP.getFileName());
        cppSourceCodeFile.createNewFile();

        cSourceCodeFile = new File(testDir, ProgrammingLanguage.C.getFileName());
        cSourceCodeFile.createNewFile();

        inputFile = new File(inputFile, "input.txt");
        FileOutputStream fos = new FileOutputStream(inputFile);
        fos.write("5".getBytes());
        fos.close();
    }

    @AfterEach
    void tearUp() {
        javaSourceCodeFile.delete();
        cppSourceCodeFile.delete();
        cSourceCodeFile.delete();
        inputFile.delete();
    }

    @Test
    @DisplayName("컴파일 에러나는 코드실행")
    void 컴파일_에러나는_코드실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(javaSourceCodeFile);
        String willFailedAtCompile = "import java.util.Scanner;\n" +
                "\n" +
                "public class Main {\n" +
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
        assertFalse(codeExecutor.compileCode(ProgrammingLanguage.JAVA, testDir));
    }

    @Test
    @DisplayName("런타임 에러나는 코드실행")
    void 런타임_에러나는_코드실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(javaSourceCodeFile);
        String willFailedAtCompile = "import java.util.Scanner;\n" +
                "\n" +
                "public class Main {\n" +
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
        codeExecutor.compileCode(ProgrammingLanguage.JAVA, testDir);
        assertEquals(1, codeExecutor.executeCompiledCode(ProgrammingLanguage.JAVA, testDir, inputFile, Limit.of(512, 2)));
    }

    @Test
    @DisplayName("정상적인 자바 코드실행")
    void 정상적_자바_코드실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(javaSourceCodeFile);
        String willFailedAtCompile = "import java.util.Scanner;\n" +
                "\n" +
                "public class Main {\n" +
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
        codeExecutor.compileCode(ProgrammingLanguage.JAVA, testDir);
        assertEquals(0, codeExecutor.executeCompiledCode(ProgrammingLanguage.JAVA, testDir, inputFile, Limit.of(512, 2)));
    }

    @Test
    @DisplayName("무한루프 코드실행")
    void 무한루프_코드실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(javaSourceCodeFile);
        String willFailedAtCompile = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"start infinite loop\");\n" +
                "        while(true){\n" +
                "        }\n" +
                "    }\n" +
                "}";
        fos.write(willFailedAtCompile.getBytes());
        fos.close();

        codeExecutor.compileCode(ProgrammingLanguage.JAVA, testDir);
        assertEquals(124, codeExecutor.executeCompiledCode(ProgrammingLanguage.JAVA, testDir, inputFile, Limit.of(512, 1)));
    }

    @Test
    @DisplayName("악성코드 실행")
    void 악성코드_실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(javaSourceCodeFile);
        String willFailedAtCompile = "import java.io.File;\n" +
                "import java.io.FileOutputStream;\n" +
                "import java.io.IOException;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) throws IOException {\n" +
                "        File file = new File(\"//test2\", \"warning.sh\");\n" +
                "        file.createNewFile();\n" +
                "        FileOutputStream fos = new FileOutputStream(file);\n" +
                "        fos.write(\"waning code\".getBytes());\n" +
                "    }\n" +
                "}";
        fos.write(willFailedAtCompile.getBytes());
        fos.close();

        codeExecutor.compileCode(ProgrammingLanguage.JAVA, testDir);
        assertEquals(1, codeExecutor.executeCompiledCode(ProgrammingLanguage.JAVA, testDir, inputFile, Limit.of(512, 1)));
    }
    // c/c++코드 실행

    @Test
    @DisplayName("정상 C 코드 실행")
    void C_코드_실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(cSourceCodeFile);
        String willRunningSuccessfully = "#include <stdio.h>\n" +
                "\n" +
                "int main() {\n" +
                "    int n;\n" +
                "    scanf(\"%d\", &n);\n" +
                "    for (int i = 0; i < n; i++) {\n" +
                "        printf(\"hello world~\\n\");\n" +
                "    }\n" +
                "    return 0;\n" +
                "}\n";
        fos.write(willRunningSuccessfully.getBytes());
        fos.close();

        codeExecutor.compileCode(ProgrammingLanguage.C, testDir);
        assertEquals(0, codeExecutor.executeCompiledCode(ProgrammingLanguage.C, testDir, inputFile, Limit.of(512, 1)));
    }

    @Test
    @DisplayName("정상 CPP 코드 실행")
    void CPP_코드_실행() throws IOException {
        FileOutputStream fos = new FileOutputStream(cppSourceCodeFile);
        String willRunningSuccessfully = "#include <iostream>\n" +
                "using namespace std;\n" +
                "\n" +
                "int main() {\n" +
                "    int n;\n" +
                "    cin >> n;\n" +
                "    for (int i = 0; i < n; i++) {\n" +
                "        cout << \"hello world\\n\";\n" +
                "    }\n" +
                "}\n";
        fos.write(willRunningSuccessfully.getBytes());
        fos.close();

        codeExecutor.compileCode(ProgrammingLanguage.CPP, testDir);
        assertEquals(0, codeExecutor.executeCompiledCode(ProgrammingLanguage.CPP, testDir, inputFile, Limit.of(512, 1)));
    }
}
