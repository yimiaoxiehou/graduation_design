/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.manager.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-12 18:27
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbExpressMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbExpress;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbExpressExample;

import java.util.Date;
import java.util.List;

@Service
public class ExpressService {

    @Autowired
    private TbExpressMapper tbExpressMapper;

    public List<TbExpress> getExpressList() {
        TbExpressExample example = new TbExpressExample();
        example.setOrderByClause("sort_order asc");
        return tbExpressMapper.selectByExample(example);
    }

    public int addExpress(TbExpress tbExpress) {
        tbExpress.setCreated(new Date());
        tbExpressMapper.insert(tbExpress);
        return 1;
    }

    public int updateExpress(TbExpress tbExpress) {
        tbExpress.setUpdated(new Date());
        tbExpressMapper.updateByPrimaryKey(tbExpress);
        return 1;
    }

    public int delExpress(int id) {
        tbExpressMapper.deleteByPrimaryKey(id);
        return 1;
    }
}
