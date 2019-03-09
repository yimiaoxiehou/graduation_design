/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 18:39
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "tk.yimiao.yimiaocloud")
@MapperScan("tk.yimiao.yimiaocloud.microservice.mall.base.mapper")
public class ManagerApplication {
    public static void main(String[] args){
        SpringApplication.run(ManagerApplication.class, args);
    }
}