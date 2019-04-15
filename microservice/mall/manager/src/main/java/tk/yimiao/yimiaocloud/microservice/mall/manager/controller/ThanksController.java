package tk.yimiao.yimiaocloud.microservice.mall.manager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.common.model.Result;
import tk.yimiao.yimiaocloud.common.util.ResultUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbThanks;
import tk.yimiao.yimiaocloud.microservice.mall.manager.service.ThanksService;

/**
 * @author Exrickx
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins = "*")
@Api(description = "捐赠管理")
public class ThanksController {

    @Autowired
    private ThanksService thanksService;

    @RequestMapping(value = "/thanks/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取捐赠列表")
    public DataTablesResult getThanksList() {

        DataTablesResult result = thanksService.getThanksList();
        return result;
    }

    @RequestMapping(value = "/thanks/count", method = RequestMethod.GET)
    @ApiOperation(value = "获取捐赠列表总数")
    public Result<Object> getThanksCount() {

        Long result = thanksService.countThanks();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/thanks/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加捐赠")
    public Result<Object> addThanks(TbThanks tbThanks) {

        thanksService.addThanks(tbThanks);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/thanks/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑捐赠")
    public Result<Object> updateThanks(TbThanks tbThanks) {

        thanksService.updateThanks(tbThanks);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/thanks/del/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除捐赠")
    public Result<Object> delThanks(@PathVariable int[] ids) {

        for (int id : ids) {
            thanksService.deleteThanks(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/thanks/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取捐赠")
    public Result<TbThanks> getThanks(@PathVariable int id) {

        TbThanks tbThanks = thanksService.getThankById(id);
        return new ResultUtil<TbThanks>().setData(tbThanks);
    }
}
