/**
 * @Package tk.yimiao.yimiaocloud.auth
 * @Description: TODO
 * @author yimiao
 * @date 2019-04-02 12:41
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.Result;
import tk.yimiao.yimiaocloud.common.oauth.github.GithubToken;
import tk.yimiao.yimiaocloud.common.oauth.github.GithubUser;
import tk.yimiao.yimiaocloud.common.util.HttpUtil;
import tk.yimiao.yimiaocloud.common.util.ResultUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.Member;
import tk.yimiao.yimiaocloud.microservice.mall.base.service.OauthService;

import java.util.HashMap;

@Slf4j
@RestController
@Api
public class GithubOauthController {

    static String ACCESS_API = "https://github.com/login/oauth/access_token";
    static String INFO_API = "https://api.github.com/user?access_token=";
    static String CLIENT_ID = "1272f0beb1bb345805e2";
    static String CLIENT_SECRET = "71fe5b4ea6c3dc940d9be56eaa5b9635094991c8";
    static String REDIRECT_URL = "/index";
    @Autowired
    OauthService oauthService;

    @RequestMapping(value = "/github/login")
    public Result<Member> loginGithub(@RequestParam String code) {
        log.info(code);
        HashMap<String, String> map = new HashMap<>();
        map.put("client_id", CLIENT_ID);
        map.put("client_secret", CLIENT_SECRET);
        map.put("code", code);
        GithubToken githubToken = JSON.parseObject(HttpUtil.sendPost(ACCESS_API, map), GithubToken.class);
        if (githubToken == null || githubToken.getToken() == null) {
            throw new BusinessException("Github oAuth 服务器错误");
        }
        GithubUser githubUser = JSON.parseObject(HttpUtil.sendGet(INFO_API + githubToken.getAccess_token()), GithubUser.class);
        if (githubUser == null || githubUser.getTrueName() == null) {
            throw new BusinessException("Github oAuth 服务器错误");
        }

        Member member = oauthService.oauthLogin(githubUser, githubToken);
        member.setToken(githubToken.getToken());

        return new ResultUtil<Member>().setData(member);
    }

    //http://github.com/login/oauth/authorize?client_id=1272f0beb1bb345805e2&scpoe=user:email
}
