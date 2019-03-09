/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 14:25
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.constant;


public enum GlobalErrorCodeEnum implements ErrorCodeEnum {

    SUCCESS(1, "OK"),

    FAILURE(0, "Failure"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    MONGO_COLLECTION_NOT_EXIT(3301, "MONGO COLLECTION NOT EXIST"),
    INVALID_PARAM(100, "参数错误"),
    UNSUPPORT_IMAGE_TYPE(110, "不支持的图片格式"),
    UNSUPPORT_STORE_PATH(111, "找不到文件"),

    MYSQL_ERROR(601,"数据库异常"),
    MYSQL_VIOLATION_ERROR(602, "用户信息与他人重复，请输入新消息"),

    EMAIL_ERROR(701,"邮件发送异常"),

    BUSINESS_PAY_INFO_SAVE_ERROR(901,"支付信息保存异常"),

    BUSINESS_ORDER_UPDATE_ERROR(911,"订单更新异常"),
    BUSINESS_ORDER_NOT_FOUND(912,"订单不存在"),
    BUSINESS_ORDER_CREATE_FAIL(913, "订单创建失败"),
    BUSINESS_ORDER_ITEM_CREATE_FAIL(914, "生成订单商品失败"),

    BUSINESS_USER_NOT_FOUND(921,"用户不存在"),

    BUSINESS_CART_CLEAR_FAIL(931,"购物车清除失败"),

    BUSINESS_SHIPPING_SAVE_FAIL(941,"物流信息保存失败");

    private final int code;
    private final String message;

    GlobalErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
