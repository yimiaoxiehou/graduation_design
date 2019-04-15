/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-17 16:12
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.constant;

import tk.yimiao.yimiaocloud.common.constant.ErrorCodeEnum;

public enum MallErrorCodeEnum implements ErrorCodeEnum {

    /**
     *
     */
    UNLOGIN(121, "未登录"),
    USER_NOT_FOUND(122, "用户不存在"),
    USER_UPDATE_FAIL(123, "用户信息更新失败"),

    MYSQL_ERROR(601, "数据库异常"),
    MYSQL_VIOLATION_ERROR(602, "用户信息与他人重复，请输入新消息"),

    EMAIL_ERROR(701, "邮件发送异常"),

    USER_REGISTR_ERROR(801, "用户注册失败"),

    BUSINESS_PAY_INFO_SAVE_ERROR(901, "支付信息保存异常"),

    BUSINESS_ORDER_UPDATE_ERROR(911, "订单更新异常"),
    BUSINESS_ORDER_NOT_FOUND(912, "订单不存在"),
    BUSINESS_ORDER_CREATE_FAIL(913, "订单创建失败"),
    BUSINESS_ORDER_ITEM_CREATE_FAIL(914, "生成订单商品失败"),

    BUSINESS_CART_CLEAR_FAIL(931, "购物车清除失败"),

    BUSINESS_SHIPPING_SAVE_FAIL(941, "物流信息保存失败"),
    BUSINESS_ADDRESS_ADD_FAIL(951, "地址添加失败"),
    BUSINESS_ADDRESS_UPDATE_FAIL(952, "地址更新失败"),
    BUSINESS_ADDRESS_DEL_FAIL(953, "地址删除失败");


    protected final int code;
    protected final String message;

    MallErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 加 1000 ，与 globalErrorCodeEnum 区别
     *
     * @return
     */
    @Override
    public int getCode() {
        return this.code + 1000;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
