/**
 * @Package tk.yimiao.yimiaocloud.common.annotation
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-28 21:36
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    String description() default "";
}
