/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.constant
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-11 23:22
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.constant;

public enum CountEnum {

    /**
     * 自定义
     */
    CUSTOM_DATE(-1),
    /**
     * 按年统计
     */
    CUSTOM_YEAR(-2),
    /**
     * 本周
     */
    THIS_WEEK(0),
    /**
     * 本月
     */
    THIS_MONTH(1),
    /**
     * 上个月
     */
    LAST_MONTH(2);

    private int code;

    CountEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
