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
import team.msa.gateway.infrastructure.exception.status.ExceptionMessage;
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
            throw new UnauthorizedException(ExceptionMessage.ExpiredToken.getMessage());
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(ExceptionMessage.MalformedToken.getMessage());
        } catch (SignatureException e) {
            throw new UnauthorizedException(ExceptionMessage.SignatureVerifyToken.getMessage());
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException(ExceptionMessage.IllegalArgumentToken.getMessage());
        } catch (Exception e) {
            throw new UnauthorizedException(ExceptionMessage.VerifyFailToken.getMessage());
        }
    }

    public String getMemberTypeFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("memberType").toString();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ExceptionMessage.ExpiredToken.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException(ExceptionMessage.UnsupportedToken.getMessage());
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(ExceptionMessage.MalformedToken.getMessage());
        } catch (SignatureException e) {
            throw new UnauthorizedException(ExceptionMessage.SignatureVerifyToken.getMessage());
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException(ExceptionMessage.IllegalArgumentToken.getMessage());
        } catch (Exception e) {
            throw new UnauthorizedException(ExceptionMessage.VerifyFailToken.getMessage());
        }
    }


}
