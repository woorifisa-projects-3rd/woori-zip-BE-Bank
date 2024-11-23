package fisa.woorizip.bank.auth;

import fisa.woorizip.bank.common.exception.WooriBankException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static fisa.woorizip.bank.member.AuthErrorCode.NOT_EXIST_ACCESS_TOKEN;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Login login = handlerMethod.getMethodAnnotation(Login.class);
        if (isNull(login)) return true;

        String authorization = request.getHeader(AUTHORIZATION);
        validateExistAccessToken(authorization);

        String accessToken = jwtTokenProvider.extractAccessToken(authorization);
        jwtTokenProvider.validToken(accessToken);

        return true;
    }

    private void validateExistAccessToken(String authorization) {
        if (isNull(authorization)) {
            throw new WooriBankException(NOT_EXIST_ACCESS_TOKEN);
        }
    }
}
