/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.manager.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-28 20:48
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbBaseMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbLogMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbOrderItemMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbShiroFilterMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.*;

import java.util.List;

@Slf4j
@Service
public class SystemService {

    @Autowired
    private TbShiroFilterMapper tbShiroFilterMapper;
    @Autowired
    private TbBaseMapper tbBaseMapper;
    @Autowired
    private TbLogMapper tbLogMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    //fixme
//    @Value("${BASE_ID}")
    @Value("1")
    private String BASE_ID;


    public List<TbShiroFilter> getShiroFilter() {

        TbShiroFilterExample example = new TbShiroFilterExample();
        example.setOrderByClause("sort_order");
        List<TbShiroFilter> list = tbShiroFilterMapper.selectByExample(example);
        if (list == null) {
            throw new BusinessException("获取shiro过滤链失败");
        }
        return list;
    }


    public Long countShiroFilter() {

        TbShiroFilterExample example = new TbShiroFilterExample();
        Long result = tbShiroFilterMapper.countByExample(example);
        if (result == null) {
            throw new BusinessException("获取shiro过滤链数目失败");
        }
        return result;
    }


    public int addShiroFilter(TbShiroFilter tbShiroFilter) {

        if (tbShiroFilterMapper.insert(tbShiroFilter) != 1) {
            throw new BusinessException("添加shiro过滤链失败");
        }
        return 1;
    }


    public int updateShiroFilter(TbShiroFilter tbShiroFilter) {

        if (tbShiroFilterMapper.updateByPrimaryKey(tbShiroFilter) != 1) {
            throw new BusinessException("更新shiro过滤链失败");
        }
        return 1;
    }


    public int deleteShiroFilter(int id) {

        if (tbShiroFilterMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除shiro过滤链失败");
        }
        return 1;
    }


    public TbBase getBase() {

        TbBase tbBase = tbBaseMapper.selectByPrimaryKey(Integer.valueOf(BASE_ID));
        if (tbBase == null) {
            throw new BusinessException("获取基础设置失败");
        }
        return tbBase;
    }


    public int updateBase(TbBase tbBase) {

        if (tbBaseMapper.updateByPrimaryKey(tbBase) != 1) {
            throw new BusinessException("更新基础设置失败");
        }
        return 1;
    }


    public TbOrderItem getWeekHot() {

        List<TbOrderItem> list = tbOrderItemMapper.getWeekHot();
        if (list == null) {
            throw new BusinessException("获取热销商品数据失败");
        }
        if (list.size() == 0) {
            TbOrderItem tbOrderItem = new TbOrderItem();
            tbOrderItem.setTotal(0);
            tbOrderItem.setTitle("暂无数据");
            tbOrderItem.setPicPath("");
            return tbOrderItem;
        }
        return list.get(0);
    }


    public int addLog(TbLog tbLog) {

        if (tbLogMapper.insert(tbLog) != 1) {
            throw new BusinessException("保存日志失败");
        }
        return 1;
    }


    public DataTablesResult getLogList(int draw, int start, int length, String search, String orderCol, String orderDir) {

        DataTablesResult result = new DataTablesResult();
        //分页
        PageHelper.startPage(start / length + 1, length);
        List<TbLog> list = tbLogMapper.selectByMulti("%" + search + "%", orderCol, orderDir);
        PageInfo<TbLog> pageInfo = new PageInfo<>(list);

        result.setRecordsFiltered((int) pageInfo.getTotal());
        result.setRecordsTotal(Math.toIntExact(countLog()));

        result.setDraw(draw);
        result.setData(list);
        return result;
    }


    public Long countLog() {

        TbLogExample example = new TbLogExample();
        Long result = tbLogMapper.countByExample(example);
        if (result == null) {
            throw new BusinessException("获取日志数量失败");
        }
        return result;
    }


    public int deleteLog(int id) {

        if (tbLogMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除日志失败");
        }
        return 1;
    }
}
