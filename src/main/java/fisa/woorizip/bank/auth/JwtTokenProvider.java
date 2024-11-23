package fisa.woorizip.bank.auth;

import fisa.woorizip.bank.common.exception.WooriBankException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static fisa.woorizip.bank.member.AuthErrorCode.EXPIRED_TOKEN;
import static fisa.woorizip.bank.member.AuthErrorCode.FAILED_SIGNATURE_TOKEN;
import static fisa.woorizip.bank.member.AuthErrorCode.INCORRECTLY_CONSTRUCTED_TOKEN;
import static fisa.woorizip.bank.member.AuthErrorCode.INCORRECT_CONSTRUCT_HEADER;
import static fisa.woorizip.bank.member.AuthErrorCode.INVALID_CLAIM_TYPE;
import static fisa.woorizip.bank.member.AuthErrorCode.MISSING_ISSUER_TOKEN;
import static fisa.woorizip.bank.member.AuthErrorCode.NOT_WOOHAENGSHI_TOKEN;
import static fisa.woorizip.bank.member.AuthErrorCode.UNSUPPORTED_TOKEN;

@Component
public class JwtTokenProvider {

    private static final String MEMBER_ID = "memberId";
    private static final String ISSUER = "woori-bank";
    private static final String TOKEN_TYPE = "Bearer";

    private SecretKey key;
    private Long accessExpiration;

    @Autowired
    public JwtTokenProvider(
            @Value("${security.jwt.key}") String key,
            @Value("${security.jwt.expiration.access}") Long accessExpiration) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(key));
        this.accessExpiration = accessExpiration;
    }

    private String createToken(Long memberId, Long expiration) {
        return Jwts.builder()
                .claims()
                .add(MEMBER_ID, memberId)
                .and()
                .signWith(key)
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, accessExpiration);
    }

    public void validToken(String token) {
        try {
            getClaimsJwt(token);
        } catch (MissingClaimException e) {
            throw new WooriBankException(MISSING_ISSUER_TOKEN);
        } catch (IncorrectClaimException e) {
            throw new WooriBankException(NOT_WOOHAENGSHI_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new WooriBankException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new WooriBankException(UNSUPPORTED_TOKEN);
        } catch (SignatureException e) {
            throw new WooriBankException(FAILED_SIGNATURE_TOKEN);
        } catch (MalformedJwtException e) {
            throw new WooriBankException(INCORRECTLY_CONSTRUCTED_TOKEN);
        }
    }

    private Jws<Claims> getClaimsJwt(String token) {
        return Jwts.parser().verifyWith(key).requireIssuer(ISSUER).build().parseClaimsJws(token);
    }

    public Long getMemberId(String token) {
        try {
            return getClaimsJwt(token).getPayload().get(MEMBER_ID, Long.class);
        } catch (RequiredTypeException e) {
            throw new WooriBankException(INVALID_CLAIM_TYPE);
        }
    }

    public String extractAccessToken(String authorization) {
        String[] tokenFormat = authorization.split(" ");
        if (tokenFormat.length != 2 && !tokenFormat[0].equals(TOKEN_TYPE)) {
            throw new WooriBankException(INCORRECT_CONSTRUCT_HEADER);
        }
        return tokenFormat[1];
    }
}
