package ac.kr.kw.judge.commons.exception;

public class EventConsumeFailedException extends RuntimeException{
    public EventConsumeFailedException() {
    }

    public EventConsumeFailedException(String message) {
        super(message);
    }
}
