package tk.yimiao.yimiaocloud.microservice.mall.manager.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 页面跳转
 *
 * @author Exrick
 * @date 2017/7/30
 */
//@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins = "*")
//@Api(description = "板块内容管理")
public class PageController {
    // fixme
//
//    @RequestMapping("/")
//    public String showIndex() {
//        return "index";
//    }
//
//    @RequestMapping("/{page}")
//    public String showPage(@PathVariable String page) {
//        return page;
//    }
}
