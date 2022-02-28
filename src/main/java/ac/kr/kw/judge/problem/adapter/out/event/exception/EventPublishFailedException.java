package ac.kr.kw.judge.problem.adapter.out.event.exception;

public class EventPublishFailedException extends RuntimeException{
    public EventPublishFailedException() {
    }

    public EventPublishFailedException(String message) {
        super(message);
    }
}
