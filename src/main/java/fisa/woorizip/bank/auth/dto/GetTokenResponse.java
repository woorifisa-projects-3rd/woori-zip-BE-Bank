package fisa.woorizip.bank.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetTokenResponse {

    private String accessToken;
    private String tokenType;

    @Builder
    private GetTokenResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = "bearer";
    }

    public static GetTokenResponse from(String accessToken) {
        return GetTokenResponse.builder().accessToken(accessToken).build();
    }
}
