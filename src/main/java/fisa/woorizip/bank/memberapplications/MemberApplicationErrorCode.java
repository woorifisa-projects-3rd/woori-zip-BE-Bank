package fisa.woorizip.bank.memberapplications;

import fisa.woorizip.bank.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum MemberApplicationErrorCode implements ErrorCode {
    MEMBER_APPLICATION_NOT_FOUND(NOT_FOUND, "회원의 해당 서비스 가입 내역을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    MemberApplicationErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
