package ac.kr.kw.judge.problem.domain;

import java.util.Arrays;

public enum ProgrammingLanguage {
    JAVA(new String[]{"javac", "Main.java"}, new String[]{"timeout", "1", "java", "-Djava.security.manager", "-cp", ".", "Main"}, "Main.java"), C(new String[]{"gcc", "-o", "Main", "Main.c"}, new String[]{"timeout", "1", "./Main"}, "Main.c"), CPP(new String[]{"g++", "-o", "Main", "Main.cpp"}, new String[]{"timeout", "1", "./Main"}, "Main.cpp");
    private String[] compileOrder;
    private String[] executeOrder;
    private String fileName;

    ProgrammingLanguage(String[] compileOrder, String[] executeOrder, String fileName) {
        this.compileOrder = compileOrder;
        this.executeOrder = executeOrder;
        this.fileName = fileName;
    }

    public String[] getCompileOrder() {
        return compileOrder;
    }

    public String[] getExecuteOrder() {
        return executeOrder;
    }

    public String getFileName() {
        return fileName;
    }

    private static void checkLanguageIsSupported(String language) {
        Arrays.stream(ProgrammingLanguage.values())
                .filter(programmingLanguage -> language.equals(programmingLanguage.toString()))
                .findFirst().orElseThrow(() -> {
            throw new IllegalArgumentException(language + "는 지원 되는 언어가 아닙니다.");
        });
    }

    public static ProgrammingLanguage ofSupportedLanguage(String language) {
        checkLanguageIsSupported(language);
        return ProgrammingLanguage.valueOf(language);
    }
}
