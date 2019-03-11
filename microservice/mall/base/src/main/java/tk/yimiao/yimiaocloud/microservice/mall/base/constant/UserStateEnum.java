/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 15:28
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.constant;

public enum UserStateEnum {
    /**
     * 用户账号状态
     */
    REGISTER(1, "注册"),
    ACTIVATE(2, "激活"),
    RESET_PASSWORD(3, "重置密码"),
    DELETE(4, "销户"),
    UNSAFE(5, "不安全，需验证"),
    OTHER(0, "正常");

    private int code;
    private String desc;

    UserStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
