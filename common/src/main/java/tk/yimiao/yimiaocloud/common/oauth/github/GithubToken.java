/**
 * @Package tk.yimiao.yimiaocloud.common.oauth.github
 * @Description: TODO
 * @author yimiao
 * @date 2019-04-02 14:16
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.oauth.github;

import lombok.Data;
import tk.yimiao.yimiaocloud.common.oauth.BaseToken;

@Data
public class GithubToken implements BaseToken {
    String access_token;
    String scope;
    String token_type;

    @Override
    public String getToken() {
        return access_token;
    }

    @Override
    public String getOauthName() {
        return "GitHub";
    }
}
