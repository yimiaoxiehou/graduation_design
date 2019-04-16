/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-09 20:24
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.util.MD5Util;
import tk.yimiao.yimiaocloud.microservice.mall.base.constant.UserStateEnum;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbMemberMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMember;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMemberExample;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RegisterService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    public boolean checkData(String param, int type) {

        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        //1：用户名 2：手机号 3：邮箱
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            return false;
        }

        List<TbMember> list = tbMemberMapper.selectByExample(example);
        return list == null || list.size() <= 0;
    }

    public int register(String userName, String userPwd) {

        TbMember tbMember = new TbMember();
        tbMember.setUsername(userName);

        if (userName.isEmpty() || userPwd.isEmpty()) {
            //用户名密码不能为空
            return -1;
        }
        boolean result = checkData(userName, 1);
        if (!result) {
            //该用户名已被注册
            return 0;
        }

        //MD5加密
        String md5Pass = MD5Util.MD5Encode(userPwd);
        tbMember.setPassword(md5Pass);
        tbMember.setState(UserStateEnum.REGISTER.getCode());
        tbMember.setCreated(new Date());
        tbMember.setUpdated(new Date());

        //EmailUtil.sendMail()

        if (tbMemberMapper.insert(tbMember) != 1) {
            log.warn(String.format("用户注册失败，用户名为 :{%s}", userName));
            throw new BusinessException(GlobalErrorCodeEnum.USER_REGISTR_ERROR);
        }
        return 1;
    }


}
