package ac.kr.kw.judge.commons.exception;

import ac.kr.kw.judge.commons.apis.ApiResult;
import ac.kr.kw.judge.commons.apis.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalStateException.class,
            IllegalArgumentException.class,})
    public ResponseEntity<ApiResult> handleBusinessException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler({CompileErrorException.class,
            ExecuteErrorException.class,
            FileUploadFailedException.class,
            FileHashFailedException.class,
            NullPointerException.class,
            SourceCodeCreateException.class})
    public ResponseEntity<ApiResult> handleSystemException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiUtils.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
            MissingServletRequestPartException.class,
            HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ApiResult> handleRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<ApiResult> handleUnAuthorizedException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiUtils.fail(HttpStatus.FORBIDDEN.value(), e.getMessage()));
    }
}
