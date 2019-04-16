/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.controller
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-11 23:12
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.yimiao.yimiaocloud.common.model.Result;
import tk.yimiao.yimiaocloud.common.util.DateUtil;
import tk.yimiao.yimiaocloud.common.util.ResultUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.ChartData;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.OrderChartData;
import tk.yimiao.yimiaocloud.microservice.mall.manager.constant.CountEnum;
import tk.yimiao.yimiaocloud.microservice.mall.manager.service.CountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins = "*")
@Api(description = "统计")
public class CountController {

    @Autowired
    private CountService countService;

    @RequestMapping(value = "/count/order", method = RequestMethod.GET)
    @ApiOperation(value = "通过panelId获得板块内容列表")
    public Result<Object> countOrder(@RequestParam int type,
                                     @RequestParam(required = false) String startTime,
                                     @RequestParam(required = false) String endTime,
                                     @RequestParam(required = false) int year) {

        ChartData data = new ChartData();
        Date startDate = null, endDate = null;
        if (type == CountEnum.CUSTOM_DATE.getCode()) {
            startDate = DateUtil.beginOfDay(DateUtil.parse(startTime));
            endDate = DateUtil.endOfDay(DateUtil.parse(endTime));
            long betweenDay = DateUtil.diff(startDate, endDate, DateUtil.DAY_MS);
            if (betweenDay > 31) {
                return new ResultUtil<Object>().setErrorMsg("所选日期范围过长，最多不能超过31天");
            }
        }
        List<OrderChartData> list = countService.getOrderCountData(type, startDate, endDate, year);
        List<Object> xDatas = new ArrayList<>();
        List<Object> yDatas = new ArrayList<>();
        BigDecimal countAll = new BigDecimal("0");
        for (OrderChartData orderData : list) {
            if (type == CountEnum.CUSTOM_YEAR.getCode()) {
                xDatas.add(DateUtil.format(orderData.getTime(), "yyyy-MM"));
            } else {
                xDatas.add(DateUtil.formatDate(orderData.getTime()));
            }
            yDatas.add(orderData.getMoney());
            countAll = countAll.add(orderData.getMoney());
        }
        data.setxDatas(xDatas);
        data.setyDatas(yDatas);
        data.setCountAll(countAll);
        return new ResultUtil<Object>().setData(data);
    }
}
