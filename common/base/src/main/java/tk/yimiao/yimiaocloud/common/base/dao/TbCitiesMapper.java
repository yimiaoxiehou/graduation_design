package tk.yimiao.yimiaocloud.common.base.dao;

import tk.yimiao.yimiaocloud.common.base.domain.TbCities;

public interface TbCitiesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCities record);

    int insertSelective(TbCities record);

    TbCities selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCities record);

    int updateByPrimaryKey(TbCities record);
}