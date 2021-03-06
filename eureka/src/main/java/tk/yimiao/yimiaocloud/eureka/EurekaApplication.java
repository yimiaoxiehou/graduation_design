package tk.yimiao.yimiaocloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author yimiao
 * @version V1.0
 * @Package tk.yimiao.yimiaocloud.eureka
 * @Description: Eureka 服务器
 * @date 2019-03-02 22:41
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
