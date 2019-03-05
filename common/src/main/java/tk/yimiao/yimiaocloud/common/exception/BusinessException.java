/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 14:32
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.exception;

import tk.yimiao.yimiaocloud.common.constant.ErrorCode;

public class BusinessException extends RuntimeException {

    private int code = 500;
    private ErrorCode errorCode;

    public BusinessException(int code) {
        this.code = code;
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable cause, ErrorCode errorCode) {
        super(formatMsg(errorCode), cause);
        this.errorCode = errorCode;
    }

    public final static String formatMsg(ErrorCode errorCodeEnum) {
        return String.format("%s-%s", errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

    public int getCode() {
        return this.code;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
