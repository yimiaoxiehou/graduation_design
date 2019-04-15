/**
 * @Package tk.yimiao.yimiaocloud.common.base.exception.handler
 * @Description: 异常处理，通过 @RestControllerAdvice 和 @ExceptionHandler 注入到 spring 的异常处理体系
 * @author yimiao
 * @date 2019-03-05 14:33
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.exception.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
import tk.yimiao.yimiaocloud.common.constant.ErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.exception.ApplicationException;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.RestResult;
import tk.yimiao.yimiaocloud.common.model.RestResultBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    private RestResult runtimeExceptionHandler(Exception ex) {
        log.error(String.format("---------> runtimeException message:{%s}", ex.getMessage()), ex);
        return RestResultBuilder.builder().failure().build();
    }

    /**
     * mybatis 不鼓励俘获异常，所以对异常进行了封装，抛出来的是 DataAccessException 不属于 SQLException 的子类
     * 具体异常信息在 DataAccessException.getCause() 中
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    private RestResult mybatisExceptionHandler(DataAccessException ex) {
        Throwable throwable = ex.getCause();
        if (throwable instanceof MySQLIntegrityConstraintViolationException) {
            log.warn(String.format("---------> MySQLIntegrityConstraintViolationException message:{%s}", ex.getMessage()), ex);
            return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.MYSQL_VIOLATION_ERROR).build();
        }
        log.warn(String.format("---------> sqlException message:{%s}", ex.getMessage()), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.MYSQL_ERROR).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private RestResult illegalParamsExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = Maps.newHashMapWithExpectedSize(bindingResult.getFieldErrorCount());
        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.warn(String.format("---------> invalid request! fields ex:{%s}", JSON.toJSONString(errors)), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.BAD_REQUEST).data(errors).build();
    }

    @ExceptionHandler(BindException.class)
    private RestResult bindExceptionHandler(BindException ex) {
        Map<String, String> errors = Maps.newHashMapWithExpectedSize(ex.getFieldErrorCount());
        ex.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.warn(String.format("---------> invalid request! fields ex:{%s}", JSON.toJSONString(errors)), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.BAD_REQUEST).data(errors).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private RestResult messageNotReadableExceptiionHandler(HttpMessageNotReadableException ex) {
        log.warn(String.format("---------> json convert failure,exception:{%s}", ex.getMessage()), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.BAD_REQUEST).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private RestResult methodArgumentExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn(String.format("---------> path variable failure,exception:{%s}", ex.getMessage()), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.BAD_REQUEST).build();
    }

    @ExceptionHandler(BusinessException.class)
    private RestResult businessExceptionHandler(BusinessException ex) {
        ErrorCodeEnum errorCodeEnum = ex.getErrorCodeEnum();
        if (ex.getErrorCodeEnum() == null) {
            log.warn(String.format("---------> business exception code:{%s}, message:{%s}", ex.getCode(), ex.getMessage()), ex);
            return RestResultBuilder.builder().code(ex.getCode()).message(ex.getMessage()).build();
        } else {
            log.warn(String.format("---------> business exception code:{%s}, message:{%s}", errorCodeEnum.getCode(), errorCodeEnum.getMessage()), ex);
            return RestResultBuilder.builder().code(errorCodeEnum.getCode()).message(errorCodeEnum.getMessage()).build();
        }
    }

    @ExceptionHandler(ApplicationException.class)
    private RestResult applicationExceptionHandler(ApplicationException ex) {
        log.error(String.format("---------> application exception message : {%s}", ex.getMessage()), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private RestResult noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        log.warn(String.format("noHandlerFoundException 404 error requestUrl:{%s}, method:{%s}, exception:{%s}", ex.getRequestURL(), ex.getHttpMethod(), ex.getMessage()), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.NOT_FOUND).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    private RestResult httpRequestMethodHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.warn(String.format("httpRequestMethodHandler 405 error requestUrl:{%s}, method:{%s}", request.getRequestURI(), ex.getMethod()), ex);
        return RestResultBuilder.builder().errorCode(GlobalErrorCodeEnum.METHOD_NOT_ALLOWED).build();
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
