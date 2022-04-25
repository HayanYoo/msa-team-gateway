package team.msa.gateway.infrastructure.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.msa.gateway.infrastructure.exception.status.UnauthorizedException;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    public boolean validateToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("토큰이 올바르게 구성되지 않았습니다.");
        } catch (SignatureException e) {
            throw new UnauthorizedException("토큰의 서명이 유효하지 않습니다.");
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("토큰의 값이 비어있습니다.");
        } catch (Exception e) {
            throw new UnauthorizedException("정상적인 토큰이 아닙니다.");
        }
    }

    public String getMemberTypeFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("memberType").toString();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException("토큰이 암호화되어 있지 않습니다.");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("토큰이 올바르게 구성되지 않았습니다.");
        } catch (SignatureException e) {
            throw new UnauthorizedException("토큰의 서명이 유효하지 않습니다.");
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("토큰의 값이 비어있습니다.");
        } catch (Exception e) {
            throw new UnauthorizedException("정상적인 토큰이 아닙니다.");
        }
    }


}
