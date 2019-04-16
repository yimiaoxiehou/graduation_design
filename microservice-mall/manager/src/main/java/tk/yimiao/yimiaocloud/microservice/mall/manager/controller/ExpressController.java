/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.controller
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-12 18:26
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.common.model.Result;
import tk.yimiao.yimiaocloud.common.util.ResultUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbExpress;
import tk.yimiao.yimiaocloud.microservice.mall.manager.service.ExpressService;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins = "*")
@Api(description = "快递")
public class ExpressController {

    @Autowired
    private ExpressService expressService;

    @RequestMapping(value = "/express/list", method = RequestMethod.GET)
    @ApiOperation(value = "获得所有快递")
    public DataTablesResult addressList() {

        DataTablesResult result = new DataTablesResult();
        List<TbExpress> list = expressService.getExpressList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/express/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加快递")
    public Result<Object> addTbExpress(TbExpress tbExpress) {

        expressService.addExpress(tbExpress);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/express/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑快递")
    public Result<Object> updateAddress(TbExpress tbExpress) {

        expressService.updateExpress(tbExpress);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/express/del/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除快递")
    public Result<Object> delAddress(@PathVariable int[] ids) {

        for (int id : ids) {
            expressService.delExpress(id);
        }
        return new ResultUtil<Object>().setData(null);
    }
}
