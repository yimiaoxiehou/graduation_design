package tk.yimiao.yimiaocloud.microservice.mall.base.mapper;

import org.apache.ibatis.annotations.Param;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbDict;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbDictExample;

import java.util.List;

public interface TbDictMapper {
    long countByExample(TbDictExample example);

    int deleteByExample(TbDictExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbDict record);

    int insertSelective(TbDict record);

    List<TbDict> selectByExample(TbDictExample example);

    TbDict selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbDict record, @Param("example") TbDictExample example);

    int updateByExample(@Param("record") TbDict record, @Param("example") TbDictExample example);

    int updateByPrimaryKeySelective(TbDict record);

    int updateByPrimaryKey(TbDict record);
}