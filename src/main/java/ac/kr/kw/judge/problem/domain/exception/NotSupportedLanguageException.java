package ac.kr.kw.judge.problem.domain.exception;

public class NotSupportedLanguageException extends RuntimeException{
    public NotSupportedLanguageException() {
    }

    public NotSupportedLanguageException(String message) {
        super(message);
    }
}
