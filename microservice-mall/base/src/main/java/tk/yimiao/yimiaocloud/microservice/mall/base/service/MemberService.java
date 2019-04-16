/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-09 20:24
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.constant.RedisKeyEnum;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.common.model.SMMSResponse;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.common.util.SMMSUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.DtoUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.Member;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.MemberDto;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbMemberMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMember;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMemberExample;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MemberService {

    @Autowired
    TbMemberMapper tbMemberMapper;
    @Autowired
    JedisUtil jedisUtil;

    /**
     * 上传图片，返回访问地址
     *
     * @param userId  用户id
     * @param token   session token
     * @param imgDate 前端 base64 加密的图片数据
     * @return
     * @throws IOException
     */
    public String imageUpload(Long userId, String token, String imgDate) throws IOException {
        // 检查参数
        if (StringUtils.isEmpty(imgDate)) {
            return "";
        }
        if (userId == null || token == null) {
            throw new BusinessException(GlobalErrorCodeEnum.INVALID_PARAM);
        }
        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(userId);
        if (tbMember == null) {
            throw new BusinessException(GlobalErrorCodeEnum.USER_NOT_FOUND);
        }
        Member member = JSON.parseObject(jedisUtil.get(RedisKeyEnum.SESSION_PRE.getKey() + token), Member.class);
        if (member == null) {
            throw new BusinessException(GlobalErrorCodeEnum.UNLOGIN);
        }

        // 上传图片，返回访问地址
        SMMSResponse response = SMMSUtil.imageUpload(userId, token, imgDate.replace("data:image/jpeg;base64,", ""));
        if (response == null || response.getCode() == "error") {
            return "";
        }
        String imgPath = response.getData().getUrl();
        member.setFile(imgPath);

        // 更新数据库和缓存
        tbMember.setFile(imgPath);
        tbMember.setUpdated(new Date());
        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new BusinessException(GlobalErrorCodeEnum.USER_UPDATE_FAIL);
        }
        jedisUtil.set(RedisKeyEnum.SESSION_PRE.getKey() + token, JSON.toJSONString(member));
        jedisUtil.expire(RedisKeyEnum.SESSION_PRE.getKey() + token, RedisKeyEnum.SESSION_EXPIRE_TIME.getTime());

        log.info(String.format("用户头像更新成功，id:{%d} url:{%s}", userId, imgPath));
        return imgPath;
    }

    public TbMember getMemberById(long memberId) {

        TbMember tbMember;
        try {
            tbMember = tbMemberMapper.selectByPrimaryKey(memberId);
        } catch (Exception e) {
            throw new BusinessException("ID获取会员信息失败");
        }
        tbMember.setPassword("");
        return tbMember;
    }


    public DataTablesResult getMemberList(int draw, int start, int length, String search,
                                          String minDate, String maxDate, String orderCol, String orderDir) {

        DataTablesResult result = new DataTablesResult();

        try {
            //分页
            PageHelper.startPage(start / length + 1, length);
            List<TbMember> list = tbMemberMapper.selectByMemberInfo("%" + search + "%", minDate, maxDate, orderCol, orderDir);
            PageInfo<TbMember> pageInfo = new PageInfo<>(list);

            for (TbMember tbMember : list) {
                tbMember.setPassword("");
            }

            result.setRecordsFiltered((int) pageInfo.getTotal());
            result.setRecordsTotal(getMemberCount().getRecordsTotal());

            result.setDraw(draw);
            result.setData(list);
        } catch (Exception e) {
            throw new BusinessException("加载用户列表失败");
        }

        return result;
    }


    public DataTablesResult getRemoveMemberList(int draw, int start, int length, String search,
                                                String minDate, String maxDate, String orderCol, String orderDir) {

        DataTablesResult result = new DataTablesResult();

        try {
            //分页执行查询返回结果
            PageHelper.startPage(start / length + 1, length);
            List<TbMember> list = tbMemberMapper.selectByRemoveMemberInfo("%" + search + "%", minDate, maxDate, orderCol, orderDir);
            PageInfo<TbMember> pageInfo = new PageInfo<>(list);

            for (TbMember tbMember : list) {
                tbMember.setPassword("");
            }

            result.setRecordsFiltered((int) pageInfo.getTotal());
            result.setRecordsTotal(getRemoveMemberCount().getRecordsTotal());

            result.setDraw(draw);
            result.setData(list);
        } catch (Exception e) {
            throw new BusinessException("加载删除用户列表失败");
        }

        return result;
    }


    public TbMember getMemberByUsername(String username) {

        List<TbMember> list;
        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        try {
            list = tbMemberMapper.selectByExample(example);
        } catch (Exception e) {
            throw new BusinessException("ID获取会员信息失败");
        }
        if (!list.isEmpty()) {
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }


    public TbMember getMemberByPhone(String phone) {

        List<TbMember> list;
        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        try {
            list = tbMemberMapper.selectByExample(example);
        } catch (Exception e) {
            throw new BusinessException("Phone获取会员信息失败");
        }
        if (!list.isEmpty()) {
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }


    public TbMember getMemberByEmail(String email) {

        List<TbMember> list;
        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        try {
            list = tbMemberMapper.selectByExample(example);
        } catch (Exception e) {
            throw new BusinessException("Email获取会员信息失败");
        }
        if (!list.isEmpty()) {
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }


    public DataTablesResult getMemberCount() {

        DataTablesResult result = new DataTablesResult();
        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(2);
        try {
            result.setRecordsTotal((int) tbMemberMapper.countByExample(example));
        } catch (Exception e) {
            throw new BusinessException("统计会员数失败");
        }

        return result;
    }


    public DataTablesResult getRemoveMemberCount() {

        DataTablesResult result = new DataTablesResult();
        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(2);
        try {
            result.setRecordsTotal((int) tbMemberMapper.countByExample(example));
        } catch (Exception e) {
            throw new BusinessException("统计移除会员数失败");
        }

        return result;
    }


    public TbMember addMember(MemberDto memberDto) {

        TbMember tbMember = DtoUtil.MemberDto2Member(memberDto);

        if (getMemberByUsername(tbMember.getUsername()) != null) {
            throw new BusinessException("用户名已被注册");
        }
        if (tbMember.getPhone() != null && getMemberByPhone(tbMember.getPhone()) != null) {
            throw new BusinessException("手机号已被注册");
        }
        if (tbMember.getEmail() != null && getMemberByEmail(tbMember.getEmail()) != null) {
            throw new BusinessException("邮箱已被注册");
        }

        tbMember.setState(1);
        tbMember.setCreated(new Date());
        tbMember.setUpdated(new Date());
        String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
        tbMember.setPassword(md5Pass);

        if (tbMemberMapper.insert(tbMember) != 1) {
            throw new BusinessException("添加用户失败");
        }
        return getMemberByUsername(tbMember.getUsername());
    }


    public TbMember updateMember(Long id, MemberDto memberDto) {

        TbMember tbMember = DtoUtil.MemberDto2Member(memberDto);
        tbMember.setId(id);
        tbMember.setUpdated(new Date());
        TbMember oldMember = getMemberById(id);
        tbMember.setState(oldMember.getState());
        tbMember.setCreated(oldMember.getCreated());
        if (tbMember.getPassword() == null || tbMember.getPassword() == "") {
            tbMember.setPassword(oldMember.getPassword());
        } else {
            String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
            tbMember.setPassword(md5Pass);
        }

        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new BusinessException("更新会员信息失败");
        }
        return getMemberById(id);
    }


    public TbMember changePassMember(Long id, MemberDto memberDto) {

        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(id);

        String md5Pass = DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes());
        tbMember.setPassword(md5Pass);
        tbMember.setUpdated(new Date());

        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new BusinessException("修改会员密码失败");
        }
        return getMemberById(id);
    }


    public TbMember alertMemberState(Long id, Integer state) {

        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(id);
        tbMember.setState(state);
        tbMember.setUpdated(new Date());

        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new BusinessException("修改会员状态失败");
        }
        return getMemberById(id);
    }


    public int deleteMember(Long id) {

        if (tbMemberMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除会员失败");
        }
        return 0;
    }


    public TbMember getMemberByEditEmail(Long id, String email) {

        TbMember tbMember = getMemberById(id);
        TbMember newTbMember = null;
        if (tbMember.getEmail() == null || !tbMember.getEmail().equals(email)) {
            newTbMember = getMemberByEmail(email);
        }
        newTbMember.setPassword("");
        return newTbMember;
    }


    public TbMember getMemberByEditPhone(Long id, String phone) {

        TbMember tbMember = getMemberById(id);
        TbMember newTbMember = null;
        if (tbMember.getPhone() == null || !tbMember.getPhone().equals(phone)) {
            newTbMember = getMemberByPhone(phone);
        }
        newTbMember.setPassword("");
        return newTbMember;
    }


    public TbMember getMemberByEditUsername(Long id, String username) {

        TbMember tbMember = getMemberById(id);
        TbMember newTbMember = null;
        if (tbMember.getUsername() == null || !tbMember.getUsername().equals(username)) {
            newTbMember = getMemberByUsername(username);
        }
        newTbMember.setPassword("");
        return newTbMember;
    }

}
