package ac.kr.kw.judge.commons.exception;

public class NotSupportedLanguageException extends RuntimeException{
    public NotSupportedLanguageException() {
    }

    public NotSupportedLanguageException(String message) {
        super(message);
    }
}
