package ac.kr.kw.judge.problem.adapter.out.execute.exception;

public class ExecuteErrorException extends RuntimeException {
    public ExecuteErrorException() {
        super("실행 도중 에러가 발생했습니다.");
    }

    public ExecuteErrorException(String message) {
        super(message);
    }
}
