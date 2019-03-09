/**
 * @Package tk.yimiao.yimiaocloud.common.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 01:16
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.constant;

public enum EmailSubjectEnum {

    ACTIVATE(1,"激活",48),
    RESETPASSWORD(2,"重置密码",2),
    PAY_CHECK(3,"支付确认",0);

    private final int type;
    private final String message;
    private final int validHour;

    EmailSubjectEnum(int type, String message, int validHour) {
        this.type = type;
        this.message = message;
        this.validHour = validHour;
    }

    public int getType(){
        return type;
    }

    public String getMessage() {
        return message;
    }

    public int getValidHour() {
        return validHour;
    }
}
