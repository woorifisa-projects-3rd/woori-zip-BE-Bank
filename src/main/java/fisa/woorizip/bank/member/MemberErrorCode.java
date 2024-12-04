package fisa.woorizip.bank.member;

import fisa.woorizip.bank.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum MemberErrorCode implements ErrorCode {
    EARNINGS_TYPE_NOT_FOUND(NOT_FOUND, "소득 유형이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),
    ALREADY_EXIST_USERNAME(CONFLICT, "이미 존재하는 아이디입니다."),
    ROLE_NOT_FOUND(NOT_FOUND, "권한(role)을 찾을 수 없습니다."),
    GENDER_NOT_FOUND(NOT_FOUND, "잘못된 성별 값입니다.");

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
