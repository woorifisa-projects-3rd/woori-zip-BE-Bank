package fisa.woorizip.bank.auth;

import fisa.woorizip.bank.common.exception.WooriBankException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static fisa.woorizip.bank.member.AuthErrorCode.NOT_EXIST_ACCESS_TOKEN;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class)
                && Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Long resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {

        String authorization = webRequest.getHeader(AUTHORIZATION);
        MemberId memberId = parameter.getParameterAnnotation(MemberId.class);
        if (isNull(memberId) || !memberId.required()) {
            return null;
        }

        validateExistAuthHeader(authorization);
        String accessToken = jwtTokenProvider.extractAccessToken(authorization);
        return jwtTokenProvider.getMemberId(accessToken);
    }

    private void validateExistAuthHeader(String authorization) {
        if (isNull(authorization)) {
            throw new WooriBankException(NOT_EXIST_ACCESS_TOKEN);
        }
    }
}
