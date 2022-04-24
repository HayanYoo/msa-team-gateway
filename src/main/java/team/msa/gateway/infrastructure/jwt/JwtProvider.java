package team.msa.gateway.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        } catch (Exception e) {
            throw new IllegalArgumentException("토큰값을 확인해 주세요");
        }
    }
}
