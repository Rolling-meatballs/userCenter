package com.murphy.backend.exception;

import com.murphy.backend.common.ErrorCode;
import lombok.Getter;

import java.io.Serial;

/**
 * custom business exception
 *
 * @author murphy
 */
@Getter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4729791629201302874L;
    private final int code;
    
    private final String description;
    
    public BusinessException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public BusinessException(String message) {
        super(message);
        this.code = ErrorCode.PARAMS_ERROR.getCode();
        this.description = ErrorCode.PARAMS_ERROR.getDescription();
    }

}
