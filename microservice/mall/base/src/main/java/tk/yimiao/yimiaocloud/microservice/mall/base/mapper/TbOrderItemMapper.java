package tk.yimiao.yimiaocloud.microservice.mall.base.mapper;

import org.apache.ibatis.annotations.Param;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbOrderItem;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbOrderItemExample;

import java.util.List;

public interface TbOrderItemMapper {
    long countByExample(TbOrderItemExample example);

    int deleteByExample(TbOrderItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbOrderItem record);

    int insertSelective(TbOrderItem record);

    List<TbOrderItem> selectByExample(TbOrderItemExample example);

    TbOrderItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

    int updateByExample(@Param("record") TbOrderItem record, @Param("example") TbOrderItemExample example);

    int updateByPrimaryKeySelective(TbOrderItem record);

    int updateByPrimaryKey(TbOrderItem record);
}