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
    CREATE(0,"创建"),
    PAY(1,"支付"),
    UNSHIP(2, "未发货"),
    SHIPPING(3,"已发货"),
    FINISH(4,"完成订单"),
    CANCEL(5,"锁定订单"),
    FAIL(6,"交易失败"),
    DELETE(9,"删除订单");

    public static boolean isNormalState(int state){
        switch (state){
            case 9:
                return false;
            default:
                return true;
        }
    }

    private int code;
    private String desc;

    OrderStateEnum(int code, String desc) {
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