package fisa.woorizip.bank.common.exception;

import fisa.woorizip.bank.common.exception.errorcode.ErrorCode;

public class WooriBankException extends RuntimeException {

    private final ErrorCode errorCode;

    public WooriBankException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
