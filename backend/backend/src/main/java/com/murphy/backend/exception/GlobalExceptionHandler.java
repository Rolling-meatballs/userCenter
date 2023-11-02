package com.murphy.backend.exception;

import com.murphy.backend.common.BaseResponse;
import com.murphy.backend.common.ErrorCode;
import com.murphy.backend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serial;

/**
 * global exception handler
 * @author murphy
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException exception) {
        log.error("businessException: {}", exception.getMessage(), exception);
        return ResultUtils.error(exception.getCode(), exception.getMessage(), exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(Exception exception) {
        log.error("runtime exception: {}", exception.getMessage());
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, exception.getMessage(), exception.getMessage());
    }
}
