package fisa.woorizip.bank.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInRequest {

    private String username;
    private String password;

    @Builder
    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected SignInRequest() {
    }
}
