package tk.yimiao.yimiaocloud.microservice.mall.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbAddress;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbAddressExample;

import java.util.List;

@Mapper
public interface TbAddressMapper {
    long countByExample(TbAddressExample example);

    int deleteByExample(TbAddressExample example);

    int deleteByPrimaryKey(Long addressId);

    int insert(TbAddress record);

    int insertSelective(TbAddress record);

    List<TbAddress> selectByExample(TbAddressExample example);

    TbAddress selectByPrimaryKey(Long addressId);

    int updateByExampleSelective(@Param("record") TbAddress record, @Param("example") TbAddressExample example);

    int updateByExample(@Param("record") TbAddress record, @Param("example") TbAddressExample example);

    int updateByPrimaryKeySelective(TbAddress record);

    int updateByPrimaryKey(TbAddress record);
}