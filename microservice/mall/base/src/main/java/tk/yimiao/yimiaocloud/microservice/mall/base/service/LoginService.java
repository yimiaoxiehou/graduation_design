/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-09 20:23
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
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
        saveToken(token, member);

        return member;
    }

    /**
     * 保存用户 token 到缓存
     * 检验用户id是否登录，
     * 是：根据id 获取其他 token ，删除 token 缓存
     * 保存登录状态
     * 保存 token 缓存
     *
     * @param token
     * @param member
     */
    public void saveToken(String token, Member member) {
        String key = RedisKeyEnum.SESSION_PRE.getKey() + token;
        List<String> hasLogin = jedisUtil.lrange(RedisKeyEnum.HASLOGIN_PRE.getKey() + member.getId(), 0, -1);
        if (hasLogin != null) {
            for (String s : hasLogin) {
                jedisUtil.del(RedisKeyEnum.SESSION_PRE.getKey() + s);
            }
            jedisUtil.del(RedisKeyEnum.HASLOGIN_PRE.getKey() + member.getId());
        }
        jedisUtil.lpush(RedisKeyEnum.HASLOGIN_PRE.getKey() + member.getId(), token);
        jedisUtil.set(key, JSON.toJSONString(member));
        jedisUtil.expire(key, RedisKeyEnum.SESSION_EXPIRE_TIME.getTime());
    }

    /**
     * 通过 token 获取用户信息
     *
     * @param token
     * @return
     */
    public Member getUserByToken(String token) {

        String json = jedisUtil.get(RedisKeyEnum.SESSION_PRE.getKey() + token);
        Member member;
        if (json != null) {
            member = JSON.parseObject(json, Member.class);
            jedisUtil.expire(RedisKeyEnum.SESSION_PRE.getKey() + token, RedisKeyEnum.SESSION_EXPIRE_TIME.getTime());
            List<String> hasLogin = jedisUtil.lrange(RedisKeyEnum.HASLOGIN_PRE.getKey() + member.getId(), 0, -1);
            if (hasLogin != null) {
                for (String s : hasLogin) {
                    if (!s.equals(token)) {
                        member = new Member();
                        member.setState(0);
                        member.setMessage("用户在其他地点登录，当前位置被迫退出.");
                    }
                }
            }
        } else {
            member = new Member();
            member.setState(0);
            member.setMessage("用户登录已过期，请重新登录");
        }
        return member;
    }

    /**
     * 退出登录
     * 移除已登录缓存
     * 移除 token 缓存
     *
     * @param token
     */
    public void logout(String token) {

        String json = jedisUtil.get(RedisKeyEnum.SESSION_PRE.getKey() + token);
        if (json != null) {
            Member member = JSON.parseObject(json, Member.class);
            jedisUtil.del(RedisKeyEnum.HASLOGIN_PRE.getKey() + member.getId());
            jedisUtil.del(RedisKeyEnum.SESSION_PRE.getKey() + token);
        }
    }
}
