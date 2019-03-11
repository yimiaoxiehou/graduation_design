package tk.yimiao.yimiaocloud.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author yimiao
 * @version V1.0
 * @Package PACKAGE_NAME
 * @Description: TODO
 * @date 2019-03-02 23:14
 */

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
        log.info("Spring-cloud-zuul-service start");
    }
}
