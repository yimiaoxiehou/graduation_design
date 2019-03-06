/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.controller
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-06 22:50
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbUser;
import tk.yimiao.yimiaocloud.microservice.mall.front.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user/all")
    @ResponseBody
    public List<TbUser> getAll(){
        return userService.selectAll();
    }
}
