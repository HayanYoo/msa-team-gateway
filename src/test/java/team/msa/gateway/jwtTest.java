package team.msa.gateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.msa.gateway.infrastructure.jwt.JwtProvider;

@SpringBootTest
public class jwtTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void successParseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJJRCI6MSwiQVVUSCI6IkFETUlOIiwiZXhwIjoxNjUwOTczODYzfQ.rKG30lEnAKj7ss0vB5MrAg3Es-DFsq6Dg4NN32iAbWg";
        boolean parse = jwtProvider.validateToken(token);

        Assertions.assertTrue(parse);
    }

    @Test
    void FailParseToken() {
        String token = "aaaasdddibbJ2IUzI1NiJ9.eyJJRCI6MSwiQVVUSCI6IkFETUlOIiwiZXhwIjoxNjUwOTczODYzfQ.rKG30lEnAKj7ss0vB5MrAg3Es-DFsq6Dg4NN32iAbWg";

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> jwtProvider.validateToken(token));
        Assertions.assertEquals("토큰값을 확인해 주세요", exception.getMessage());

    }
}
