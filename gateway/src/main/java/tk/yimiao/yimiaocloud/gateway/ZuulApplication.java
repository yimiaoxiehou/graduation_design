package tk.yimiao.yimiaocloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Package PACKAGE_NAME
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-02 23:14
 * @version V1.0
 */

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args){
        SpringApplication.run(ZuulApplication.class, args);
    }
}