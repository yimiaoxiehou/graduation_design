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
    USER_PRE("user_pre:"),
    SESSION_PRE("session:"),
    PRODUCT_HOME("product_home"),
    PRODUCT_ITEM_PRE("product_item:"),
    RECOMEED_PANEL("recomeed_panel"),
    THANK_PANEL("thank_panel"),
    HEADER_PANEL("header_panel"),
    SESSION_EXPIRE_TIME(1800);

    private String key;
    private int time;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    RedisKeyEnum(int time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public int getTime() {
        return time;
    }
}
