package fisa.woorizip.bank.member;

import fisa.woorizip.bank.auth.Login;
import fisa.woorizip.bank.auth.MemberId;
import fisa.woorizip.bank.member.dto.ShowMemberDataResponse;
import fisa.woorizip.bank.member.dto.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/api/v1/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignInRequest signInRequest) {
        memberService.signUp(signInRequest);
        return ResponseEntity.ok().build();
    }

    @Login
    @GetMapping("/api/v1/members")
    public ShowMemberDataResponse getMemberData(@MemberId Long memberId) {
        ShowMemberDataResponse memberData = memberService.getMemberData(memberId);
        return memberData;
    }
}
