package ac.kr.kw.judge.commons.exception;

public class FileUploadFailedException extends RuntimeException{
    public FileUploadFailedException() {
    }

    public FileUploadFailedException(String message) {
        super(message);
    }
}
