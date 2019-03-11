/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception.model
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 14:43
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.model;

import tk.yimiao.yimiaocloud.common.constant.ErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;

/**
 * @param <T>
 */
public class RestResultBuilder<T> {
    protected int code = 200;
    protected String message;
    protected T data;

    public static RestResultBuilder builder() {
        RestResultBuilder restResultBuilder = new RestResultBuilder();
        return restResultBuilder;
    }

    public RestResultBuilder code(int code) {
        this.code = code;
        return this;
    }

    public RestResultBuilder message(String message) {
        this.message = message;
        return this;
    }

    public RestResultBuilder data(T data) {
        this.data = data;
        return this;
    }

    public void initErrorCode(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }

    public RestResultBuilder errorCode(ErrorCodeEnum errorCodeEnum) {
        initErrorCode(errorCodeEnum);
        return this;
    }

    public RestResultBuilder success() {
        initErrorCode(GlobalErrorCodeEnum.SUCCESS);
        return this;
    }

    public RestResultBuilder success(T data) {
        initErrorCode(GlobalErrorCodeEnum.SUCCESS);
        this.data = data;
        return this;
    }

    public RestResultBuilder failure() {
        initErrorCode(GlobalErrorCodeEnum.FAILURE);
        return this;
    }

    public RestResultBuilder failure(T data) {
        initErrorCode(GlobalErrorCodeEnum.FAILURE);
        this.data = data;
        return this;
    }

    public RestResultBuilder success(Boolean result) {
        if (result) {
            return this.success();
        } else {
            return this.failure();
        }
    }

    public RestResult<T> build() {
        return new RestResult<T>(this.code, this.message, this.data);
    }

    public RestResult build(RestResult restResult) {
        return restResult;
    }
}
