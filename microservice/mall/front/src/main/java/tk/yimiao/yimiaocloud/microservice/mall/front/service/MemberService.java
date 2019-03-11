/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-09 20:24
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.constant.RedisKeyEnum;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.SMMSResponse;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.common.util.SMMSUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.Member;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbMemberMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMember;

import java.io.*;
import java.util.Date;

@Slf4j
@Service
public class MemberService {

    @Autowired
    TbMemberMapper tbMemberMapper;
    @Autowired
    JedisUtil jedisUtil;

    /**
     * 上传图片，返回访问地址
     * @param userId 用户id
     * @param token session token
     * @param imgDate 前端 base64 加密的图片数据
     * @return
     * @throws IOException
     */
    public String imageUpload(Long userId, String token, String imgDate) throws IOException {
        // 检查参数
        if (StringUtils.isEmpty(imgDate)){
            return "";
        }
        if (userId == null || token == null){
            throw new BusinessException(GlobalErrorCodeEnum.INVALID_PARAM);
        }
        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(userId);
        if (tbMember == null){
            throw  new BusinessException(GlobalErrorCodeEnum.USER_NOT_FOUND);
        }
        Member member = JSON.parseObject(jedisUtil.get(RedisKeyEnum.SESSION_PRE.getKey()+token), Member.class);
        if (member == null){
            throw new BusinessException(GlobalErrorCodeEnum.UNLOGIN);
        }

        // 上传图片，返回访问地址
        SMMSResponse response = SMMSUtil.imageUpload(userId, token, imgDate.replace("data:image/jpeg;base64,",""));
        if (response == null || response.getCode() == "error") {
            return "";
        }
        String imgPath = response.getData().getUrl();
        member.setFile(imgPath);

        // 更新数据库和缓存
        tbMember.setFile(imgPath);
        tbMember.setUpdated(new Date());
        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1){
            throw new BusinessException(GlobalErrorCodeEnum.USER_UPDATE_FAIL);
        }
        jedisUtil.set(RedisKeyEnum.SESSION_PRE.getKey()+token, JSON.toJSONString(member));
        jedisUtil.expire(RedisKeyEnum.SESSION_PRE.getKey()+token, RedisKeyEnum.SESSION_EXPIRE_TIME.getTime());

        log.info(String.format("用户头像更新成功，id:{%d} url:{%s}",userId,imgPath));
        return imgPath;
    }

}
