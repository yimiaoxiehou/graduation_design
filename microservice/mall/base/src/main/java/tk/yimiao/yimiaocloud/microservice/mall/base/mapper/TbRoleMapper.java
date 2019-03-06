package tk.yimiao.yimiaocloud.microservice.mall.base.mapper;

import org.apache.ibatis.annotations.Param;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbRole;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbRoleExample;

import java.util.List;

public interface TbRoleMapper {
    long countByExample(TbRoleExample example);

    int deleteByExample(TbRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbRole record);

    int insertSelective(TbRole record);

    List<TbRole> selectByExample(TbRoleExample example);

    TbRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbRole record, @Param("example") TbRoleExample example);

    int updateByExample(@Param("record") TbRole record, @Param("example") TbRoleExample example);

    int updateByPrimaryKeySelective(TbRole record);

    int updateByPrimaryKey(TbRole record);
}