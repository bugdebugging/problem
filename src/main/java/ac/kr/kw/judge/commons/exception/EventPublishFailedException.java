package ac.kr.kw.judge.commons.exception;

public class EventPublishFailedException extends RuntimeException{
    public EventPublishFailedException() {
    }

    public EventPublishFailedException(String message) {
        super(message);
    }
}
