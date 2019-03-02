package tk.yimiao.yimiaocloud.common.base.dao;

import tk.yimiao.yimiaocloud.common.base.domain.TbAddress;

public interface TbAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbAddress record);

    int insertSelective(TbAddress record);

    TbAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbAddress record);

    int updateByPrimaryKey(TbAddress record);
}