/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.controller
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-11 23:34
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.controller;

import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.common.model.Result;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.common.util.ResultUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbDict;
import tk.yimiao.yimiaocloud.microservice.mall.manager.constant.DictEnum;
import tk.yimiao.yimiaocloud.microservice.mall.manager.service.DictService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins = "*")
@Api(description = "词典库")
public class DictController {

    @Autowired
    private DictService dictService;

    @Autowired
    private JedisUtil jedisUtil;

    @RequestMapping(value = "/getDictList", method = RequestMethod.GET)
    @ApiOperation(value = "获得所有扩展词库")
    public String getDictExtList(HttpServletResponse response) {

        String result = "";
        String v = jedisUtil.get(DictEnum.EXT_KEY.getKey());
        if (StringUtils.isNotBlank(v)) {
            return v;
        }
        List<TbDict> list = dictService.getDictList();
        for (TbDict tbDict : list) {
            result += tbDict.getDict() + "\n";
        }
        if (StringUtils.isNotBlank(result)) {
            jedisUtil.set(DictEnum.EXT_KEY.getKey(), result);
        }
        response.addHeader(DictEnum.LAST_MODIFIED.getKey(), jedisUtil.get(DictEnum.LAST_MODIFIED.getKey()));
        response.addHeader(DictEnum.ETAG.getKey(), jedisUtil.get(DictEnum.ETAG.getKey()));
        return result;
    }

    @RequestMapping(value = "/getStopDictList", method = RequestMethod.GET)
    @ApiOperation(value = "获得所有扩展词库")
    public String getStopDictList(HttpServletResponse response) {

        String result = "";
        String v = jedisUtil.get(DictEnum.STOP_KEY.getKey());
        if (StringUtils.isNotBlank(v)) {
            return v;
        }
        List<TbDict> list = dictService.getStopList();
        for (TbDict tbDict : list) {
            result += tbDict.getDict() + "\n";
        }
        if (StringUtils.isNotBlank(result)) {
            jedisUtil.set(DictEnum.STOP_KEY.getKey(), result);
        }
        response.addHeader(DictEnum.LAST_MODIFIED.getKey(), jedisUtil.get(DictEnum.LAST_MODIFIED.getKey()));
        response.addHeader(DictEnum.ETAG.getKey(), jedisUtil.get(DictEnum.ETAG.getKey()));
        return result;
    }

    @RequestMapping(value = "/dict/list", method = RequestMethod.GET)
    @ApiOperation(value = "获得所有扩展词库")
    public DataTablesResult getDictList() {

        DataTablesResult result = new DataTablesResult();
        List<TbDict> list = dictService.getDictList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/dict/stop/list", method = RequestMethod.GET)
    @ApiOperation(value = "获得所有停用词库")
    public DataTablesResult getStopList() {

        DataTablesResult result = new DataTablesResult();
        List<TbDict> list = dictService.getStopList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/dict/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加词典")
    public Result<Object> addDict(TbDict tbDict) {

        dictService.addDict(tbDict);
        update();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/dict/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑词典")
    public Result<Object> updateDict(TbDict tbDict) {

        dictService.updateDict(tbDict);
        update();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/dict/del/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除词典")
    public Result<Object> delDict(@PathVariable int[] ids) {

        for (int id : ids) {
            dictService.delDict(id);
        }
        update();
        return new ResultUtil<Object>().setData(null);
    }

    public void update() {
        //更新词典标识
        jedisUtil.set(DictEnum.LAST_MODIFIED.getKey(), String.valueOf(System.currentTimeMillis()));
        jedisUtil.set(DictEnum.ETAG.getKey(), String.valueOf(System.currentTimeMillis()));
        //更新缓存
        jedisUtil.del(DictEnum.EXT_KEY.getKey());
        jedisUtil.del(DictEnum.STOP_KEY.getKey());
    }
}
