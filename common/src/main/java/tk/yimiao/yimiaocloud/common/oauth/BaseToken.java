package tk.yimiao.yimiaocloud.common.oauth;

/**
 * @author yimiao
 * @version V1.0
 * @Package tk.yimiao.yimiaocloud.common.oauth
 * @Description: TODO
 * @date 2019-04-02 15:10
 */
public interface BaseToken {
    /**
     * 获取 token 的统一封装
     *
     * @return
     */
    String getToken();

    /**
     * 获取 oauthName
     *
     * @return
     */
    String getOauthName();
}
