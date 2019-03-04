/**
 * @Package tk.yimiao.yimiaocloud.common.base
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-04 21:21
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("tk.yimiao.yimiaocloud.common.base.dao")
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }
}
