package fisa.woorizip.bank.auth;

import fisa.woorizip.bank.auth.dto.GetTokenRequest;
import fisa.woorizip.bank.auth.dto.GetTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;
    private static final String REDIRECT_FORMAT = "%s?code=%s";

    @PostMapping("/auth")
    public ResponseEntity<Void> authenticate(@RequestParam Long memberId,
                                             @RequestParam String responseType,
                                             @RequestParam String clientId,
                                             @RequestParam String redirectUri) {
        String authorizationCode = authService.getAuthorizationCode(memberId, responseType, clientId, redirectUri);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, String.format(REDIRECT_FORMAT, "http://localhost:3000/api/oauthRedirect", authorizationCode))
                .build();
    }

    @PostMapping("/token")
    public GetTokenResponse getToken(@RequestBody GetTokenRequest getTokenRequest) {
        return authService.getToken(getTokenRequest);
    }

}
