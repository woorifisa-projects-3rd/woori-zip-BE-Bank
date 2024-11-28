package fisa.woorizip.bank.member;

import fisa.woorizip.bank.member.dto.SignInRequest;
import fisa.woorizip.bank.member.dto.SignInResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpHeaders.LOCATION;

@Controller
@RequiredArgsConstructor
@RequestMapping("/woori-bank")
public class MemberController {

    private final MemberService memberService;
    private static final String CLIENT_ID = "client_id";
    private static final String MEMBER_ID = "member_id";

    @GetMapping("/auth")
    public String signIn(@RequestParam String responseType,
                         @RequestParam String clientId,
                         @RequestParam String redirectUri,
                         HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        memberService.validateServiceAuth(responseType, clientId, redirectUri);
        session.setAttribute(CLIENT_ID, clientId);
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Void> signInPost(@ModelAttribute SignInRequest signInRequest, HttpSession session) {
        String clientId = (String) session.getAttribute(CLIENT_ID);
        SignInResult signInResult = memberService.signIn(signInRequest, clientId);
        return createSignInResponse(signInResult, session);
    }

    private ResponseEntity<Void> createRedirectResponse(String redirectUri) {
        return ResponseEntity.status(HttpStatus.FOUND).header(LOCATION, redirectUri).build();
    }

    private static final String SIGN_IN_REDIRECT_FORMAT = "http://localhost:3000/api/oauthRedirect?code=%s";
    private static final String REDIRECT_AGREEMENT = "http://localhost:8082/woori-bank/agreement";

    private ResponseEntity<Void> createSignInResponse(SignInResult signInResult, HttpSession session) {
        if(signInResult.isAlreadySignedIn())
            return createRedirectResponse(String.format(SIGN_IN_REDIRECT_FORMAT, signInResult.getAuthCode()));
        session.setAttribute(MEMBER_ID, signInResult.getMemberId());
        return createRedirectResponse(REDIRECT_AGREEMENT);
    }

    @GetMapping("/agreement")
    public String consent() {
        return "agreementForm";
    }

    @ResponseBody
    @PostMapping("/agreement")
    public ResponseEntity<Void> consentPost(HttpSession session) {
        Long memberId = (Long) session.getAttribute(MEMBER_ID);
        String clientId = (String) session.getAttribute(CLIENT_ID);
        String authCode = memberService.getAuthCode(memberId, clientId);
        return createRedirectResponse(String.format(SIGN_IN_REDIRECT_FORMAT, authCode));
    }

}
