package fisa.woorizip.bank.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetTokenRequest {

    @NotBlank private String grantType;
    @NotBlank private String clientId;
    @NotBlank private String redirectUri;
    @NotBlank private String code;
    @NotBlank private String clientSecret;

    @Builder
    private GetTokenRequest(String grantType, String clientId, String redirectUri, String code, String clientSecret) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.code = code;
        this.clientSecret = clientSecret;
    }

    protected GetTokenRequest() {
    }
}
