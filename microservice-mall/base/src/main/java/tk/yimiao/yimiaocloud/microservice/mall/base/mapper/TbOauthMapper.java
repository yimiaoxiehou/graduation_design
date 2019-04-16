package tk.yimiao.yimiaocloud.microservice.mall.base.mapper;

import org.apache.ibatis.annotations.Param;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbOauth;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbOauthExample;

import java.util.List;

public interface TbOauthMapper {
    long countByExample(TbOauthExample example);

    int deleteByExample(TbOauthExample example);

    int insert(TbOauth record);

    int insertSelective(TbOauth record);

    List<TbOauth> selectByExample(TbOauthExample example);

    int updateByExampleSelective(@Param("record") TbOauth record, @Param("example") TbOauthExample example);

    int updateByExample(@Param("record") TbOauth record, @Param("example") TbOauthExample example);
}