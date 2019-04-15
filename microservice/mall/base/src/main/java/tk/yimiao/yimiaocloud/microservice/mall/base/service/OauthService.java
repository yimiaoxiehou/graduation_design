/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-04-02 14:50
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.oauth.BaseToken;
import tk.yimiao.yimiaocloud.common.oauth.BaseUser;
import tk.yimiao.yimiaocloud.common.util.UUIDGenerator;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.DtoUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.Member;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.MemberDto;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbMemberMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbOauthMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMember;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbOauth;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbOauthExample;

import java.util.List;

@Slf4j
@Service
public class OauthService {

    @Autowired
    TbOauthMapper tbOauthMapper;
    @Autowired
    TbMemberMapper tbMemberMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    LoginService loginService;

    public TbOauth selectByOauthId(String oauthId, String oauthName) {

        TbOauthExample example = new TbOauthExample();
        TbOauthExample.Criteria criteria = example.createCriteria();
        criteria.andOauthIdEqualTo(oauthId)
                .andOauthNameEqualTo(oauthName);
        List<TbOauth> list;
        try {
            list = tbOauthMapper.selectByExample(example);
        } catch (Exception e) {
            throw new BusinessException("OauthID获取信息失败");
        }
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public Member oauthLogin(BaseUser baseUser, BaseToken baseToken) {
        TbOauth tbOauth = selectByOauthId(baseUser.getOauthID(), baseToken.getOauthName());
        TbMember tbMember;
        if (tbOauth == null) {
            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(baseToken.getOauthName() + ":" + baseUser.getTrueName());
            memberDto.setEmail(baseUser.getTrueEmail());
            memberDto.setPhone(baseUser.getTruePhone());
            memberDto.setPassword(UUIDGenerator.getUUID());
            tbMember = memberService.addMember(memberDto);

            tbOauth = new TbOauth();
            tbOauth.setOauthId(baseUser.getOauthID());
            tbOauth.setOauthName(baseToken.getOauthName());
            tbOauth.setUserId(String.valueOf(tbMember.getId()));
            if (tbOauthMapper.insert(tbOauth) == -1) {
                throw new BusinessException("oauth 插入失败");
            }
        } else {
            tbMember = tbMemberMapper.selectByPrimaryKey(Long.valueOf(tbOauth.getUserId()));
        }
        Member member = DtoUtil.TbMemer2Member(tbMember);
        loginService.saveToken(baseToken.getToken(), member);
        return member;
    }
}
