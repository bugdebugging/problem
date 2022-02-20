package ac.kr.kw.judge.commons.exception;

import ac.kr.kw.judge.commons.apis.ApiResult;
import ac.kr.kw.judge.commons.apis.ApiUtils;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.CompileErrorException;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.ExecuteErrorException;
import ac.kr.kw.judge.problem.adapter.out.execute.exception.FileHashFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ApiResult handleBusinessException(Exception e) {
        return ApiUtils.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({CompileErrorException.class,
            ExecuteErrorException.class,
            FileHashFailedException.class,
            NullPointerException.class})
    public ApiResult handleSystemException(Exception e) {
        return ApiUtils.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
