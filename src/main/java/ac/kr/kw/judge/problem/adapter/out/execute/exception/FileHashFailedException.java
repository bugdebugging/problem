package ac.kr.kw.judge.problem.adapter.out.execute.exception;

public class FileHashFailedException extends RuntimeException{
    public FileHashFailedException() {
        super("File Hash값을 가져오는데 실패했습니다.");
    }

    public FileHashFailedException(String message) {
        super(message);
    }
}
