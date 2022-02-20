package ac.kr.kw.judge.problem.dto;

public enum GradingResult {
    SUCCESS("SUCCESS"),FAILED("FAILED"),COMPILE_ERROR("COMPILE_ERROR")
    , TIME_LIMIT("TIME_LIMIT"),RUNTIME_ERROR("RUNTIME_ERROR");
    private String value;

    GradingResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
