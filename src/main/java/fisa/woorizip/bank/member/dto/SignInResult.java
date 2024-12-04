package fisa.woorizip.bank.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResult {

    private boolean isAlreadySignedIn;
    private String authCode;
    private Long memberId;

    @Builder
    public SignInResult(boolean isAlreadySignedIn, String authCode, Long memberId) {
        this.isAlreadySignedIn = isAlreadySignedIn;
        this.authCode = authCode;
        this.memberId = memberId;
    }
}
