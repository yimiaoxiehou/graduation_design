/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-09 20:23
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.constant.RedisKeyEnum;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.common.util.MD5Util;
import tk.yimiao.yimiaocloud.common.util.UUIDGenerator;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.DtoUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.Member;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbMemberMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMember;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMemberExample;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private TbMemberMapper tbMemberMapper;
    @Autowired
    private JedisUtil jedisUtil;


    public Member userLogin(String username, String password) {

        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria().andUsernameEqualTo(username);

        List<TbMember> tbMembers = tbMemberMapper.selectByExample(example);
        if (tbMembers == null || tbMembers.size() == 0) {
            Member member = new Member();
            member.setState(0);
            member.setMessage("用户名或密码错误");
            return member;
        }

        TbMember tbMember = tbMembers.get(0);
        if (MD5Util.MD5Encode(password).equals(tbMember.getPassword())) {
            Member member = new Member();
            member.setState(0);
            member.setMessage("用户名或密码错误");
            return member;
        }

        String token = UUIDGenerator.getUUID();
        Member member = DtoUtil.TbMemer2Member(tbMember);
        member.setToken(token);
        member.setState(1);

        jedisUtil.set(RedisKeyEnum.SESSION_PRE.getKey() + token, JSON.toJSONString(member));
        jedisUtil.expire(RedisKeyEnum.SESSION_PRE.getKey() + token, RedisKeyEnum.SESSION_EXPIRE_TIME.getTime());

        return member;
    }

    public Member getUserByToken(String token) {

        String json = jedisUtil.get(RedisKeyEnum.SESSION_PRE.getKey() + token);
        if (json != null) {
            Member member = JSON.parseObject(json, Member.class);
            jedisUtil.expire(RedisKeyEnum.SESSION_PRE.getKey() + token, RedisKeyEnum.SESSION_EXPIRE_TIME.getTime());
            return member;
        }
        Member member = new Member();
        member.setState(0);
        member.setMessage("用户登录已过期，请重新登录");
        return member;
    }

    public void logout(String token) {

        jedisUtil.del(RedisKeyEnum.SESSION_PRE.getKey() + token);
    }
}
