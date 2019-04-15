/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception
 * @Description: 自定义系统异常
 * @author yimiao
 * @date 2019-03-05 14:30
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
