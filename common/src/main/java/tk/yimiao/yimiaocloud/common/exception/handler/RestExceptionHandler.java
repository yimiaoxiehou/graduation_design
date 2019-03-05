/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception.handler
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 14:33
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.exception.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import tk.yimiao.yimiaocloud.common.exception.ApplicationException;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.base.exception.ErrorCode;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCode;
import tk.yimiao.yimiaocloud.common.model.RestResult;
import tk.yimiao.yimiaocloud.common.model.RestResultBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler
    private RestResult runtimeExceptionHandler(Exception ex) {
        log.error(getStackTrace(ex.getCause(), ex));
        return RestResultBuilder.builder().failure().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private RestResult illegalParamsExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = Maps.newHashMapWithExpectedSize(bindingResult.getFieldErrorCount());
        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.warn("---------> invalid request! fields ex:{}", JSON.toJSONString(errors));
        return RestResultBuilder.builder().errorCode(GlobalErrorCode.BAD_REQUEST).data(errors).build();
    }

    @ExceptionHandler(BindException.class)
    private RestResult bindExceptionHandler(BindException ex) {
        Map<String, String> errors = Maps.newHashMapWithExpectedSize(ex.getFieldErrorCount());
        ex.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.warn("---------> invalid request! fields ex:{}", JSON.toJSONString(errors));
        return RestResultBuilder.builder().errorCode(GlobalErrorCode.BAD_REQUEST).data(errors).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private RestResult messageNotReadableExceptiionHandler(HttpMessageNotReadableException ex) {
        log.warn("---------> json convert failure,exception:{}", ex.getMessage());
        return RestResultBuilder.builder().errorCode(GlobalErrorCode.BAD_REQUEST).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private RestResult methodArgumentExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("---------> path variable failure,exception:{}", ex.getMessage());
        return RestResultBuilder.builder().errorCode(GlobalErrorCode.BAD_REQUEST).build();
    }

    @ExceptionHandler(BusinessException.class)
    private RestResult businessExceptionHandler(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCodeEnum();
        if (ex.getErrorCodeEnum() == null) {
            log.warn("---------> business exception code:{}, message:{}", ex.getCode(), ex.getMessage());
            return RestResultBuilder.builder().code(ex.getCode()).message(ex.getMessage()).build();
        } else {
            log.warn("---------> business exception code:{}, message:{}", errorCode.getCode(), errorCode.getMessage());
            return RestResultBuilder.builder().code(errorCode.getCode()).message(errorCode.getMessage()).build();
        }
    }

    @ExceptionHandler(ApplicationException.class)
    private RestResult applicationExceptionHandler(ApplicationException ex) {
        log.error("---------> application exception message:" + ex.getMessage(), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCode.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private RestResult noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        log.warn("noHandlerFoundException 404 error requestUrl:{}, method:{}, exception:{}", ex.getRequestURL(), ex.getHttpMethod(), ex.getMessage());
        return RestResultBuilder.builder().errorCode(GlobalErrorCode.NOT_FOUND).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    private RestResult httpRequestMethodHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.warn("httpRequestMethodHandler 405 error requestUrl:{}, method:{}, exception:{}", request.getRequestURI(), ex.getMethod());
        return RestResultBuilder.builder().errorCode(GlobalErrorCode.METHOD_NOT_ALLOWED).build();
    }

    /**
     * 获取堆栈信息
     *
     * @param cause
     * @param ex
     * @return
     */
    private String getStackTrace(Throwable cause, Exception ex) {
        try (PrintWriter pw = new PrintWriter(new StringWriter())) {
            cause.printStackTrace(pw);
            return pw.toString();
        } catch (Exception e) {

        }
        return "";
    }

}
