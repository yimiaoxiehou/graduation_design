/**
 * @Package tk.yimiao.yimiaocloud.gateway.swagger
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 23:04
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.gateway.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置类
 */
@EnableSwagger2
@Configuration
public class Config {

    /**
     * 是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
     */
    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnable;

    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通用 api 接口文档")
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnable).select()
                // 扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("tk.yimiao.yimiaocloud"))
                // 指定路径处理，PathSelectors.any() 表示所有路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }

    /**
     * 设置 api 信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("YimiaoCloud api 文档")
                .description("yimiaoxiehou | 一秒邂逅")
                .contact(new Contact("yimiao", "http://yimiao.tk", "yimiaoxiehou@gmail.com"))
                .version("1.0.0")
                .build();
    }

}
