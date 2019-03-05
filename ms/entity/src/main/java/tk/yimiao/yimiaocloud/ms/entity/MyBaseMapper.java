package tk.yimiao.yimiaocloud.ms.entity;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author yimiao
 * @version V1.0
 * @Package tk.yimiao.yimiaocloud.common.base
 * @Description: TODO
 * @date 2019-03-04 22:16
 */
public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
