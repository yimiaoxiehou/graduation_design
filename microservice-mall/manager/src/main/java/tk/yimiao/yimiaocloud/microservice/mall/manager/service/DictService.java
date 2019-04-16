/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-11 23:36
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbDictMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbDict;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbDictExample;
import tk.yimiao.yimiaocloud.microservice.mall.manager.constant.DictEnum;

import java.util.List;

@Service
public class DictService {
    @Autowired
    private TbDictMapper tbDictMapper;

    public List<TbDict> getDictList() {

        TbDictExample example = new TbDictExample();
        TbDictExample.Criteria criteria = example.createCriteria();
        //条件查询
        criteria.andTypeEqualTo(DictEnum.DICT_EXT);
        List<TbDict> list = tbDictMapper.selectByExample(example);
        return list;
    }

    public List<TbDict> getStopList() {

        TbDictExample example = new TbDictExample();
        TbDictExample.Criteria criteria = example.createCriteria();
        //条件查询
        criteria.andTypeEqualTo(DictEnum.DICT_STOP);
        List<TbDict> list = tbDictMapper.selectByExample(example);
        return list;
    }

    public int addDict(TbDict tbDict) {

        tbDictMapper.insert(tbDict);
        return 1;
    }

    public int updateDict(TbDict tbDict) {

        tbDictMapper.updateByPrimaryKey(tbDict);
        return 1;
    }

    public int delDict(int id) {

        tbDictMapper.deleteByPrimaryKey(id);
        return 1;
    }
}
