package team.msa.gateway.infrastructure.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.msa.gateway.infrastructure.exception.status.UnauthorizedException;
import team.msa.gateway.infrastructure.filter.MemberType;
import team.msa.gateway.infrastructure.jwt.JwtProvider;

@SpringBootTest
public class JwtVerifyTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void successParseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwibWVtYmVyVHlwZSI6IkFETUlOIiwiZXhwIjoxNjUxMDI0NDgzfQ.7xt0pAJ63uL8e_BDj84prjgtlItlDpT0JuPuMopUc5k";
        boolean parse = jwtProvider.validateToken(token);

        Assertions.assertTrue(parse);
    }

    @Test
    void FailParseToken() {
        String token = "aaaasdddibbJ2IUzI1NiJ9.eyJJRCI6MSwiQVVUSCI6IkFETUlOIiwiZXhwIjoxNjUwOTczODYzfQ.rKG30lEnAKj7ss0vB5MrAg3Es-DFsq6Dg4NN32iAbWg";

        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class, () -> jwtProvider.validateToken(token));
        Assertions.assertEquals("401 UNAUTHORIZED \"토큰이 올바르게 구성되지 않았습니다.\"", exception.getMessage());

    }

    @Test
    void getMemberTypeFromTokenTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6MSwibWVtYmVyVHlwZSI6IkFETUlOIiwiZXhwIjoxNjUxMDI0NDgzfQ.7xt0pAJ63uL8e_BDj84prjgtlItlDpT0JuPuMopUc5k";
        String memberType = jwtProvider.getMemberTypeFromToken(token);

        Assertions.assertEquals(MemberType.ADMIN.getName(), memberType);
    }
}
