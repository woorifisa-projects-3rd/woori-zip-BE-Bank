package fisa.woorizip.bank.auth;

import fisa.woorizip.bank.application.Application;
import fisa.woorizip.bank.application.ApplicationRepository;
import fisa.woorizip.bank.auth.dto.GetTokenRequest;
import fisa.woorizip.bank.auth.dto.GetTokenResponse;
import fisa.woorizip.bank.common.exception.WooriBankException;
import fisa.woorizip.bank.member.Member;
import fisa.woorizip.bank.member.MemberRepository;
import fisa.woorizip.bank.memberapplications.MemberApplications;
import fisa.woorizip.bank.memberapplications.MemberApplicationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static fisa.woorizip.bank.application.ApplicationsErrorCode.APPLICATION_NOT_FOUND;
import static fisa.woorizip.bank.application.ApplicationsErrorCode.INVALID_APPLICATION;
import static fisa.woorizip.bank.member.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String RESPONSE_TYPE = "code";

    private final MemberRepository memberRepository;
    private final ApplicationRepository applicationRepository;
    private final MemberApplicationsRepository memberApplicationsRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String getAuthorizationCode(Long memberId, String responseType, String clientId, String redirectUri) {
        Member member = findMemberById(memberId);
        Application application = findApplicationsByClientId(clientId);
        validateApplication(application, responseType, redirectUri);
        String authorizationCode = UUID.randomUUID().toString();
        memberApplicationsRepository.save(createMemberApplications(authorizationCode, application, member));
        return authorizationCode;
    }

    private void validateApplication(Application application, String responseType, String redirectUri) {
        if(!application.getRedirectUrl().equals(redirectUri) || !responseType.equals(RESPONSE_TYPE)) {
            throw new WooriBankException(INVALID_APPLICATION);
        }
    }

    private MemberApplications createMemberApplications(String authorizationCode, Application application, Member member) {
        return MemberApplications.builder()
                .code(authorizationCode)
                .application(application)
                .member(member)
                .build();
    }

    private Application findApplicationsByClientId(String clientId) {
        return applicationRepository.findByClientId(clientId)
                .orElseThrow(() -> new WooriBankException(APPLICATION_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new WooriBankException(MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public GetTokenResponse getToken(GetTokenRequest getTokenRequest) {
        Member member = findMemberByCode(getTokenRequest.getCode());
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        return GetTokenResponse.from(accessToken);
    }

    private Member findMemberByCode(String code) {
        return memberRepository.findByCode(code)
                .orElseThrow(() -> new WooriBankException(MEMBER_NOT_FOUND));
    }
}
