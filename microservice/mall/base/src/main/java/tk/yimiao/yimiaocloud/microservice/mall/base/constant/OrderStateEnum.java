/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-08 15:52
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.constant;

public enum OrderStateEnum {
    /**
     * 订单状态
     */
    //之前忘记设置常量了 将就这样看吧 0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭
    CREATE(0, "未付款"),
    PAY(1, "已付款"),
    UNSHIP(2, "未发货"),
    SHIPPING(3, "已发货"),
    FINISH(4, "交易成功"),
    CANCEL(5, "交易关闭"),
    FAIL(6, "交易失败"),
    DELETE(9, "删除订单");

    private int code;
    private String desc;

    OrderStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static boolean isNormalState(int state) {
        switch (state) {
            case 9:
                return false;
            default:
                return true;
        }
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}