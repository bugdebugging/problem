package ac.kr.kw.judge;

import ac.kr.kw.judge.problem.domain.Limit;
import ac.kr.kw.judge.problem.dto.Submit;
import ac.kr.kw.judge.problem.engine.CodeExecution;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class EduTest {
    @Test
    void testFileToHash() {
        File target = new File("D:\\intellij-workspace", "createdByMe.txt");
        try {
            FileInputStream fis = new FileInputStream(target);
            String result = Base64.getEncoder().encodeToString(DigestUtils.md5Digest(fis));
            System.out.println(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void testJavaSandBox() {
        SecurityManager sm = System.getSecurityManager();
    }
    @Test
    void runCode(){
        System.out.println("start");
        long startTime=System.currentTimeMillis();
        Submit submit=Submit.of("import java.util.Scanner;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner sc = new Scanner(System.in);\n" +
                "        int numOfRepeat = sc.nextInt();\n" +
                "        for(int i=0; i<numOfRepeat; i++){\n" +
                "            System.out.println(\"Hello World\");\n" +
                "        }\n" +
                "    }\n" +
                "}","java");
        CodeExecution.run(Limit.of(256,2), submit);
        long endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
    }
}
