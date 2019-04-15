/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception.model
 * @Description: rest 返回数据模型
 * @author yimiao
 * @date 2019-03-05 14:56
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 统一返回结果 RestResult
 */
@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T extends Object> implements Serializable {
    /**
     * 返回编码
     */
    @JSONField(ordinal = 1)
    private int code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public RestResult() {
    }

    public RestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResult(code=" + this.code + ", message=" + this
                .message + ", data=" + JSON.toJSONString(this.data) + ")";
    }
}
