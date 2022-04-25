package team.msa.gateway.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Value("${msa.client.url.member}")
    private String memberUrl;
    @Value("${msa.client.url.community}")
    private String communityUrl;
    @Value("${msa.client.url.lecture}")
    private String lectureUrl;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        return builder.routes()
            .route(predicateSpec ->
                predicateSpec.path("/member/admin/**") // 회원 Domain Server ADMIN 라우터 등록
                    .filters(gatewayFilterSpec ->
                        gatewayFilterSpec
                            .addRequestHeader("member-request", "member-request-header")
                            .addResponseHeader("member-response", "member-response-header")
                    )
                    .uri(memberUrl)
            )
            .route(predicateSpec ->
                predicateSpec.path("/member/**") // 회원 Domain Server 라우터 등록
                    .uri(memberUrl)
            )
            .route(predicateSpec ->
                predicateSpec.path("/community/admin/**") // 게시판 Domain Server ADMIN 라우터 등록
                    .filters(gatewayFilterSpec ->
                        gatewayFilterSpec
                            .addRequestHeader("community-request", "community-request-header")
                            .addResponseHeader("community-response", "community-response-header")
                    )
                    .uri(communityUrl)
            )
            .route(predicateSpec ->
                predicateSpec.path("/community/**") // 게시판 Domain Server 라우터 등록
                    .uri(communityUrl)
            )
            .route(predicateSpec ->
                predicateSpec.path("/lecture/admin/**") // 강의 Domain Server ADMIN 라우터 등록
                    .filters(gatewayFilterSpec ->
                        gatewayFilterSpec
                            .addRequestHeader("lecture-request", "lecture-request-header")
                            .addResponseHeader("lecture-response", "lecture-response-header")
                    )
                    .uri(lectureUrl)
            )
            .route(predicateSpec ->
                predicateSpec.path("/lecture/**") // 강의 Domain Server 라우터 등록
                    .uri(lectureUrl)
            )
            .build();
    }
}
