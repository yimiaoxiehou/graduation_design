/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-06 22:48
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("tk.yimiao.yimiaocloud.microservice.mall.base.mapper")
public class FrontApplication {
    public static void main(String[] args){
        SpringApplication.run(FrontApplication.class, args);
    }
}
