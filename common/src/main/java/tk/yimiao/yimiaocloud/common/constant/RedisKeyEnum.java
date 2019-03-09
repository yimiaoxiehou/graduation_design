/**
 * @Package tk.yimiao.yimiaocloud.common.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-08 20:18
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.constant;

public enum RedisKeyEnum {
    /**
     *
     */
    CART_PRE("cart_pre:"),
    USER_PRE("user_pre:");

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
