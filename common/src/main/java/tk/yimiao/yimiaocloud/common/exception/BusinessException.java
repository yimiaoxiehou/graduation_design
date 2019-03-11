/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 14:32
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.exception;

import tk.yimiao.yimiaocloud.common.constant.ErrorCodeEnum;

public class BusinessException extends RuntimeException {

    private int code = 500;
    private ErrorCodeEnum errorCodeEnum;

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    public BusinessException(Throwable cause, ErrorCodeEnum errorCodeEnum) {
        super(formatMsg(errorCodeEnum), cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public final static String formatMsg(ErrorCodeEnum errorCodeEnum) {
        return String.format("%s-%s", errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

    public int getCode() {
        return this.code;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return this.errorCodeEnum;
    }
}
