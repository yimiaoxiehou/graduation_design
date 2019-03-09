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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import tk.yimiao.yimiaocloud.common.constant.EmailSubjectEnum;
import tk.yimiao.yimiaocloud.common.exception.ApplicationException;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.util.EmailUtil;
import tk.yimiao.yimiaocloud.common.util.HmacSha1Util;
import tk.yimiao.yimiaocloud.microservice.mall.base.constant.UserStateEnum;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbUser;
import tk.yimiao.yimiaocloud.microservice.mall.front.service.UserService;

import java.util.Date;
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


    @RequestMapping("/user/reg")
    @ResponseBody
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) String phone,
                           @RequestParam String email
                           ){
        TbUser tbUser = new TbUser();
        tbUser.setUsername(username);
        tbUser.setPassword(password);
        tbUser.setPhone(phone);
        tbUser.setEmail(email);
        tbUser.setState(UserStateEnum.REGISTER.getCode());
        Date now = new Date();
        tbUser.setCreated(now);
        tbUser.setUpdated(now);
        tbUser.setDescription("普通用户");
        userService.insert(tbUser);
        String signature = HmacSha1Util.getSignature(username+":"+now.getTime());
        String activeLink = String.format("http://mail.yimiao.tk/user/active?username=%s&sendtime=%s&signature=%s",username,now.getTime(),signature);
        boolean success = EmailUtil.sendMail(email,activeLink, EmailSubjectEnum.ACTIVATE);
        if (success){
            return "注册成功，请前往邮箱打开激活链接，完成激活";
        }else {
            return "注册成功，但激活邮件发送异常";
        }
    }


}
