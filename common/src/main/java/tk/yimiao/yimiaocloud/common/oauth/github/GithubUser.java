/**
 * @Package tk.yimiao.yimiaocloud.common.oauth.github
 * @Description: TODO
 * @author yimiao
 * @date 2019-04-02 14:17
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.oauth.github;

import lombok.Data;
import tk.yimiao.yimiaocloud.common.oauth.BaseUser;

@Data
public class GithubUser implements BaseUser {
    String login;
    String id;
    String node_id;
    String avatar_url;
    String gravatar_id;
    String url;
    String html_url;
    String followers_url;
    String following_url;
    String gists_url;
    String starred_url;
    String subscriptions_url;
    String organizations_url;
    String repos_url;
    String events_url;
    String received_events_url;
    String type;
    String site_admin;
    String name;
    String company;
    String blog;
    String location;
    String email;
    String hireable;
    String bio;
    String public_repos;
    String public_gists;
    String followers;
    String following;
    String created_at;
    String updated_at;

    @Override
    public String getTrueName() {
        return login != null ? login : "";
    }

    @Override
    public String getTruePhone() {
        return "";
    }

    @Override
    public String getTrueEmail() {
        return email != null ? email : "";
    }

    @Override
    public String getOauthID() {
        return id;
    }
}
