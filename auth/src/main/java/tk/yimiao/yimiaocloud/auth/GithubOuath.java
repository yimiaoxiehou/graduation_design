/**
 * @Package tk.yimiao.yimiaocloud.auth
 * @Description: TODO
 * @author yimiao
 * @date 2019-04-02 12:41
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tk.yimiao.yimiaocloud.common.util.HttpUtil;

import java.util.HashMap;

@Slf4j
public class GithubOuath {

    String url = "https://github.com/login/oauth/access_token";

    @GetMapping("login/github")
    public String loginGithub(String code, Model model) {
        log.info(code);
        System.out.println("code: " + code);
        String url = "https://github.com/login/oauth/access_token";

        HashMap<String, String> map = new HashMap<>();
        map.put("client_id", "1272f0beb1bb345805e2");
        map.put("client_secret", "71fe5b4ea6c3dc940d9be56eaa5b9635094991c8");
        map.put("code", code);
        String jsonObject = HttpUtil.sendPost(url, map);
        System.out.println("jsonObject:" + jsonObject);
        model.addAttribute("result", jsonObject);
        return "login/success";
    }

}
