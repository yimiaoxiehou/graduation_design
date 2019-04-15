package tk.yimiao.yimiaocloud.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author yimiao
 * @version V1.0
 * @Package PACKAGE_NAME
 * @Description: TODO
 * @date 2019-03-02 23:14
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@Slf4j
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
        log.info("Spring-cloud-zuul-service start");
    }

    @Bean
    public PreRequestLogFilter preRequestLogFilter() {
        return new PreRequestLogFilter();
    }
}
