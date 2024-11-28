package fisa.woorizip.bank.member;

import fisa.woorizip.bank.application.Application;
import fisa.woorizip.bank.application.ApplicationRepository;
import fisa.woorizip.bank.common.exception.WooriBankException;
import fisa.woorizip.bank.member.dto.ShowMemberDataResponse;
import fisa.woorizip.bank.member.dto.SignInRequest;
import fisa.woorizip.bank.member.dto.SignInResult;
import fisa.woorizip.bank.memberapplications.MemberApplications;
import fisa.woorizip.bank.memberapplications.MemberApplicationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static fisa.woorizip.bank.application.ApplicationsErrorCode.APPLICATION_NOT_FOUND;
import static fisa.woorizip.bank.member.AuthErrorCode.FAIL_TO_SIGN_IN;
import static fisa.woorizip.bank.member.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationRepository applicationRepository;
    private final MemberApplicationsRepository memberApplicationsRepository;

    @Transactional
    public SignInResult signIn(SignInRequest signInRequest, String clientId) {
        Member member = findMemberByRequest(signInRequest);
        return memberApplicationsRepository.findByMemberIdAndClientId(member.getId(), clientId)
                .map(
                        memberApplications -> {
                            memberApplications.updateCode(createAuthorizationCode());
                            return SignInResult.builder().isAlreadySignedIn(true).authCode(memberApplications.getCode()).memberId(member.getId()).build();
                        })
                .orElseGet(() -> SignInResult.builder().isAlreadySignedIn(false).memberId(member.getId()).build());

    }

    private Application findApplicationByClientId(String clientId) {
        return applicationRepository.findByClientId(clientId).orElseThrow(() -> new WooriBankException(APPLICATION_NOT_FOUND));
    }

    private Member findMemberByRequest(final SignInRequest signInRequest) {
        final Member member = findMemberByUsername(signInRequest.getUsername());
        validateCorrectPassword(signInRequest.getPassword(), member.getPassword());
        return member;
    }

    private Member findMemberByUsername(final String username) {
        return memberRepository
                .findByUsername(username)
                .orElseThrow(() -> new WooriBankException(MEMBER_NOT_FOUND));
    }

    private void validateCorrectPassword(final String rawPassword, final String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new WooriBankException(FAIL_TO_SIGN_IN);
        }
    }

    public void signUp(SignInRequest signInRequest) {
        Member member = Member.builder()
                .username(signInRequest.getUsername())
                .password(passwordEncoder.encode(signInRequest.getPassword()))
                .build();
        memberRepository.save(member);
    }

    public ShowMemberDataResponse getMemberData(Long memberId) {
        Member member = memberRepository.findByIdWithHistory(memberId)
                .orElseThrow(() -> new WooriBankException(MEMBER_NOT_FOUND));
        return ShowMemberDataResponse.from(member);
    }

    @Transactional
    public String getAuthCode(Long memberId, String clientId) {
        Member member = findMemberById(memberId);
        Application application = findApplicationByClientId(clientId);
        MemberApplications memberApplication = memberApplicationsRepository.save(createMemberApplication(member, application));
        return memberApplication.getCode();
    }

    private MemberApplications createMemberApplication(Member member, Application application) {
        return MemberApplications.builder()
                .member(member)
                .application(application)
                .code(createAuthorizationCode())
                .isAgree(true)
                .build();
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new WooriBankException(MEMBER_NOT_FOUND));
    }

    private String createAuthorizationCode() {
        return UUID.randomUUID().toString();
    }

    private static final String RESPONSE_TYPE = "code";

    public void validateServiceAuth(String responseType, String clientId, String redirectUri) {
        Application application = applicationRepository.findByClientId(clientId).orElseThrow(() -> new WooriBankException(APPLICATION_NOT_FOUND));
        if (!application.getRedirectUrl().equals(redirectUri) || !responseType.equals(RESPONSE_TYPE)) {
            throw new WooriBankException(APPLICATION_NOT_FOUND);
        }
    }
}
