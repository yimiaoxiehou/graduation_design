/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-11 23:22
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.util.DateUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.OrderChartData;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbOrderMapper;
import tk.yimiao.yimiaocloud.microservice.mall.manager.constant.CountEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CountService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    public List<OrderChartData> getOrderCountData(int type, Date startTime, Date endTime, int year) {

        List<OrderChartData> fullData = new ArrayList<>();
        //作者就是不想用case
        if (type == CountEnum.THIS_WEEK.getCode()) {
            //本周
            List<OrderChartData> data = tbOrderMapper.selectOrderChart(DateUtil.getBeginDayOfWeek(), DateUtil.getEndDayOfWeek());
            fullData = getFullData(data, DateUtil.getBeginDayOfWeek(), DateUtil.getEndDayOfWeek());
        } else if (type == CountEnum.THIS_MONTH.getCode()) {
            //本月
            List<OrderChartData> data = tbOrderMapper.selectOrderChart(DateUtil.getBeginDayOfMonth(), DateUtil.getEndDayOfMonth());
            fullData = getFullData(data, DateUtil.getBeginDayOfMonth(), DateUtil.getEndDayOfMonth());
        } else if (type == CountEnum.LAST_MONTH.getCode()) {
            //上个月
            List<OrderChartData> data = tbOrderMapper.selectOrderChart(DateUtil.getBeginDayOfLastMonth(), DateUtil.getEndDayOfLastMonth());
            fullData = getFullData(data, DateUtil.getBeginDayOfLastMonth(), DateUtil.getEndDayOfLastMonth());
        } else if (type == CountEnum.CUSTOM_DATE.getCode()) {
            //自定义
            List<OrderChartData> data = tbOrderMapper.selectOrderChart(startTime, endTime);
            fullData = getFullData(data, startTime, endTime);
        } else if (type == CountEnum.CUSTOM_YEAR.getCode()) {
            List<OrderChartData> data = tbOrderMapper.selectOrderChartByYear(year);
            fullData = getFullYearData(data, year);
        }
        return fullData;
    }

    /**
     * 无数据补0
     *
     * @param startTime
     * @param endTime
     */
    public List<OrderChartData> getFullData(List<OrderChartData> data, Date startTime, Date endTime) {

        List<OrderChartData> fullData = new ArrayList<>();
        //相差
        long betweenDay = DateUtil.diff(startTime, endTime, DateUtil.DAY_MS);
        //起始时间
        Date everyday = startTime;
        int count = -1;
        for (int i = 0; i <= betweenDay; i++) {
            boolean flag = true;
            for (OrderChartData chartData : data) {
                if (DateUtil.isSameDay(chartData.getTime(), everyday)) {
                    //有数据
                    flag = false;
                    count++;
                    break;
                }
            }
            if (!flag) {
                fullData.add(data.get(count));
            } else {
                OrderChartData orderChartData = new OrderChartData();
                orderChartData.setTime(everyday);
                orderChartData.setMoney(new BigDecimal("0"));
                fullData.add(orderChartData);
            }

            //时间+1天
            Calendar cal = Calendar.getInstance();
            cal.setTime(everyday);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            everyday = cal.getTime();
        }
        return fullData;
    }

    /**
     * 无数据补0
     *
     * @param data
     * @param year
     * @return
     */
    public List<OrderChartData> getFullYearData(List<OrderChartData> data, int year) {

        List<OrderChartData> fullData = new ArrayList<>();
        //起始月份
        Date everyMonth = DateUtil.getBeginDayOfYear(year);
        int count = -1;
        for (int i = 0; i < 12; i++) {
            boolean flag = true;
            for (OrderChartData chartData : data) {
                if (DateUtil.month(chartData.getTime()) == DateUtil.month(everyMonth)) {
                    //有数据
                    flag = false;
                    count++;
                    break;
                }
            }
            if (!flag) {
                fullData.add(data.get(count));
            } else {
                OrderChartData orderChartData = new OrderChartData();
                orderChartData.setTime(everyMonth);
                orderChartData.setMoney(new BigDecimal("0"));
                fullData.add(orderChartData);
            }

            //时间+1天
            Calendar cal = Calendar.getInstance();
            cal.setTime(everyMonth);
            cal.add(Calendar.MONTH, 1);
            everyMonth = cal.getTime();
        }
        return fullData;
    }
}
