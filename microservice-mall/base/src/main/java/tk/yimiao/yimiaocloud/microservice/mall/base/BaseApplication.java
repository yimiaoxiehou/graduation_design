/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 18:37
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "tk.yimiao.yimiaocloud")
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}