/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception
 * @Description: 自定义参数异常
 * @author yimiao
 * @date 2019-03-05 14:31
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.exception;

public class ArgumentException extends RuntimeException {

    public ArgumentException() {
    }

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentException(Throwable cause) {
        super(cause);
    }

    public ArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
