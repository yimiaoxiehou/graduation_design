package tk.yimiao.yimiaocloud.common.util;

import org.junit.Test;

/**
 * @author yimiao
 * @version V1.0
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: TODO
 * @date 2019-03-12 18:05
 */
public class DateUtilTest {

    @Test
    public void getEndDayOfWeek() {
        System.out.println(DateUtil.getEndDayOfWeek());
        System.out.println(DateUtil.getEndDayOfMonth());
        System.out.println(DateUtil.getBeginDayOfMonth());
        System.out.println(DateUtil.getBeginDayOfLastMonth());
        System.out.println(DateUtil.getEndDayOfLastMonth());
    }
}