package tk.yimiao.yimiaocloud.common.base.dao;

import tk.yimiao.yimiaocloud.common.base.domain.TbAdreas;

public interface TbAdreasMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbAdreas record);

    int insertSelective(TbAdreas record);

    TbAdreas selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbAdreas record);

    int updateByPrimaryKey(TbAdreas record);
}