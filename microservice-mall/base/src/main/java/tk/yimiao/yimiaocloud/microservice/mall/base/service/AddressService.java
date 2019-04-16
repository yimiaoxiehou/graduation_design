/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-10 19:35
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbAddressMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbAddress;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbAddressExample;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AddressService {

    @Autowired
    TbAddressMapper tbAddressMapper;

    public List<TbAddress> getAddressList(Long userId) {
        TbAddressExample example = new TbAddressExample();
        TbAddressExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<TbAddress> addresses = tbAddressMapper.selectByExample(example);

        if (addresses == null || addresses.size() == 0) {
            return null;
        }

        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i).getIsDefault()) {
                Collections.swap(addresses, 0, i);
                break;
            }
        }
        return addresses;
    }

    public TbAddress getAddress(Long addressId) {
        TbAddress address = tbAddressMapper.selectByPrimaryKey(addressId);
        if (address == null) {
            address = new TbAddress();
        }
        return address;
    }

    public int addAddress(TbAddress tbAddress) {
        setOneDefault(tbAddress);
        if (tbAddressMapper.insert(tbAddress) != 1) {
            log.warn(String.format("地址添加失败，地址信息为 : {%s}", JSON.toJSONString(tbAddress)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ADDRESS_ADD_FAIL);
        }
        return 1;
    }

    public int updateAddress(TbAddress tbAddress) {
        setOneDefault(tbAddress);
        if (tbAddressMapper.updateByPrimaryKey(tbAddress) != 1) {
            log.warn(String.format("地址更新失败，地址信息为 : {%s}", JSON.toJSONString(tbAddress)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ADDRESS_ADD_FAIL);
        }
        return 1;
    }

    public int delAddress(TbAddress tbAddress) {
        if (tbAddressMapper.deleteByPrimaryKey(tbAddress.getAddressId()) != 1) {
            log.warn(String.format("地址删除失败，地址信息为 : {%s}", JSON.toJSONString(tbAddress)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ADDRESS_DEL_FAIL);
        }
        return 1;
    }

    public void setOneDefault(TbAddress tbAddress) {
        //设置唯一默认
        if (tbAddress.getIsDefault()) {
            TbAddressExample example = new TbAddressExample();
            TbAddressExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(tbAddress.getUserId());
            List<TbAddress> list = tbAddressMapper.selectByExample(example);
            for (TbAddress tbAddress1 : list) {
                tbAddress1.setIsDefault(false);
                tbAddressMapper.updateByPrimaryKey(tbAddress1);
            }
        }
    }

}
