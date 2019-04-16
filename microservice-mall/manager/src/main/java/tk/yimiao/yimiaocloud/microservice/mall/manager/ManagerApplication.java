/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 18:39
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "tk.yimiao.yimiaocloud")
@MapperScan("tk.yimiao.yimiaocloud.microservice.mall.base.mapper")
@EnableDiscoveryClient
@EnableSwagger2
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}