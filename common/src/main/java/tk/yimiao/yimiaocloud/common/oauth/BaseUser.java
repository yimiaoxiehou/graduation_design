package tk.yimiao.yimiaocloud.common.oauth;

/**
 * @author yimiao
 * @version V1.0
 * @Package tk.yimiao.yimiaocloud.common.oauth.github
 * @Description: TODO
 * @date 2019-04-02 15:08
 */
public interface BaseUser {

    /**
     * 名字
     *
     * @return
     */
    String getTrueName();

    /**
     * 手机
     *
     * @return
     */
    String getTruePhone();

    /**
     * 邮箱
     *
     * @return
     */
    String getTrueEmail();

    /**
     * 获取 oauthid
     *
     * @return
     */
    String getOauthID();

}
