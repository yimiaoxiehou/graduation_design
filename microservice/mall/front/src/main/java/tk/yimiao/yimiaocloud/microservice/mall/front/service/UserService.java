/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-06 22:36
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbUserMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbAddressExample;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbUser;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbUserExample;

import java.util.List;

@Service
public class UserService {

    @Autowired
    TbUserMapper tbUserMapper;

    public void insert(TbUser tbUser){
        tbUserMapper.insert(tbUser);
    }

    public void update(TbUser tbUser){
        tbUserMapper.updateByPrimaryKey(tbUser);
    }

    public List<TbUser> selectAll(){
        TbUserExample example = new TbUserExample();
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        return tbUsers;
    }

}
