/**
 * @Package tk.yimiao.yimiaocloud.zipkin
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 18:36
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}

