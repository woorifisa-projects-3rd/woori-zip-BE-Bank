package fisa.woorizip.bank.member;

import fisa.woorizip.bank.member.dto.SignInRequest;
import fisa.woorizip.bank.member.dto.SignInResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/woori-bank")
public class MemberController {

    private final MemberService memberService;
    private static final String CLIENT_ID = "client_id";
    private static final String MEMBER_ID = "member_id";
    private static final String REDIRECT_URI = "redirect_uri";
    private final String agreementUri;

    @Autowired
    public MemberController(MemberService memberService,
                            @Value("${woori-bank.agreement}") String agreementUri) {
        this.memberService = memberService;
        this.agreementUri = agreementUri;
    }

    @GetMapping("/auth")
    public String signIn(@RequestParam String responseType,
                         @RequestParam String clientId,
                         @RequestParam String redirectUri,
                         HttpServletRequest request) {
        System.out.println("redirectUri = " + redirectUri);
        HttpSession session = request.getSession(true);
        memberService.validateServiceAuth(responseType, clientId, redirectUri);
        System.out.println("/woori-bank/auth 검증 완료");
        session.setAttribute(CLIENT_ID, clientId);
        session.setAttribute(REDIRECT_URI, redirectUri);
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

    private static final String SIGN_IN_REDIRECT_FORMAT = "%s?code=%s";

    private ResponseEntity<Void> createSignInResponse(SignInResult signInResult, HttpSession session) {
        if(signInResult.isAlreadySignedIn())
            return createRedirectResponse(String.format(SIGN_IN_REDIRECT_FORMAT, session.getAttribute(REDIRECT_URI), signInResult.getAuthCode()));
        session.setAttribute(MEMBER_ID, signInResult.getMemberId());
        return createRedirectResponse(agreementUri);
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
