/**
 * @Package tk.yimiao.yimiaocloud.ms.service.api.user
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 15:51
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.ms.service.api.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam String userName, @RequestParam String password, @RequestParam(defaultValue = "/") String next,@RequestParam(defaultValue = "false") boolean rememberMe ) {


    }

}
