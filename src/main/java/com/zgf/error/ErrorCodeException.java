package com.zgf.error;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zgf
 * Date 2018/12/2 16:25
 * Description
 */
public class ErrorCodeException extends RuntimeException implements IErrorCode {
    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String message;

    public ErrorCodeException(IErrorCode iErrorCode) {
        this.code = iErrorCode.getCode();
        this.message = iErrorCode.getMessage();
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String toString() {
        return "ErrorCodeException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
