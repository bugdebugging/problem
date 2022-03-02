package ac.kr.kw.judge.commons.exception;

public class CompileErrorException extends RuntimeException{
    public CompileErrorException() {
        super("Compile 에러 발생했습니다.");
    }

    public CompileErrorException(String message) {
        super(message);
    }
}
