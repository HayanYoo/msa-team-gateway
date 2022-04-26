package team.msa.gateway.infrastructure.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

// DefaultErrorAttributes는 스프링이 자동으로 만들어내는 에러를 담고 있다.
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> map = super.getErrorAttributes(request, options);

        Throwable throwable = getError(request);
        if (throwable instanceof GlobalException) {
            // 사용자 정의 에러일 경우, GlobalException을 통해 따로 처리된다.
            GlobalException ex = (GlobalException) getError(request);
            map.put("rt", ex.getStatus().value());
            map.put("rtMsg", ex.getReason());
            return map;
        }

        map.put("rt", HttpStatus.INTERNAL_SERVER_ERROR.value());
        map.put("rtMsg", "서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다.");
        return map;
    }
}
