package fisa.woorizip.bank.application;

import fisa.woorizip.bank.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ApplicationsErrorCode implements ErrorCode {
    APPLICATION_NOT_FOUND(NOT_FOUND, "어플리케이션을 찾을 수 없습니다."),
    INVALID_APPLICATION(BAD_REQUEST, "잘못된 어플리케이션 정보입니다.");

    private final HttpStatus status;
    private final String message;

    ApplicationsErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
