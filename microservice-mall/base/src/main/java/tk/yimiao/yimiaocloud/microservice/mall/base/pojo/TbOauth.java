package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;

import java.io.Serializable;

/**
 * tb_oauth
 *
 * @author
 */
public class TbOauth implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userId;
    private String oauthName;
    private String oauthId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOauthName() {
        return oauthName;
    }

    public void setOauthName(String oauthName) {
        this.oauthName = oauthName;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TbOauth other = (TbOauth) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getOauthName() == null ? other.getOauthName() == null : this.getOauthName().equals(other.getOauthName()))
                && (this.getOauthId() == null ? other.getOauthId() == null : this.getOauthId().equals(other.getOauthId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOauthName() == null) ? 0 : getOauthName().hashCode());
        result = prime * result + ((getOauthId() == null) ? 0 : getOauthId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", oauthName=").append(oauthName);
        sb.append(", oauthId=").append(oauthId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}