package study.msa.msadesigntaskgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // Eureka Client 역할을 수행한다. YAML에 명시한 유레카 서버에 해당 애플리케이션을 등록한다.
public class MsaDesignTaskGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsaDesignTaskGatewayApplication.class, args);
    }

}
