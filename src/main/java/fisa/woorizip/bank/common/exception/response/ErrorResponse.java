package fisa.woorizip.bank.common.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fisa.woorizip.bank.common.exception.errorcode.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValidErrorResponse> errors;

    @Builder
    private ErrorResponse(
            int status, String message, String name, List<ValidErrorResponse> errors) {
        this.status = status;
        this.message = message;
        this.name = name;
        this.errors = errors;
    }

    public ErrorResponse(int status, String message, String name) {
        this.status = status;
        this.message = message;
        this.name = name;
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .message(message)
                .name(errorCode.name())
                .build();
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage())
                .name(errorCode.name())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, List<FieldError> errors) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage())
                .name(errorCode.name())
                .errors(errors.stream().map(ValidErrorResponse::from).toList())
                .build();
    }
}
