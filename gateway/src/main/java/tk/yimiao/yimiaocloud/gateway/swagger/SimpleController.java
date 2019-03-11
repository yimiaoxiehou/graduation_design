/**
 * @Package tk.yimiao.yimiaocloud.gateway.swagger
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 23:41
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.gateway.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Api(tags = "zuul 内部 rest api")
public class SimpleController {

    @GetMapping("/hello")
    @ApiOperation(value = "demo 示例", notes = "demo notes ")
    @ApiImplicitParam(name = "name", value = "名称", example = "example")
    public String hello(String name) {
        return "hi," + name + " , this is zuul api.";
    }

}
