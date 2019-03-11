/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-10 22:26
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.constant.RedisKeyEnum;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.CartProduct;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.DtoUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbItemMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbItem;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private TbItemMapper itemMapper;

    public int addCart(long userId, long itemId, int num) {

        //hash: "key:用户id" field："商品id" value："商品信息"
        Boolean hexists = jedisUtil.hexists(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(itemId));
        //如果存在数量相加
        if (hexists) {
            String json = jedisUtil.hget(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(itemId));
            if (json != null) {
                CartProduct cartProduct = JSON.parseObject(json, CartProduct.class);
                cartProduct.setProductNum(cartProduct.getProductNum() + num);
                jedisUtil.hset(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(itemId), JSON.toJSONString(cartProduct));
            } else {
                return 0;
            }

            return 1;
        }
        //如果不存在，根据商品id取商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        if (item == null) {
            return 0;
        }
        CartProduct cartProduct = DtoUtil.TbItem2CartProduct(item);
        cartProduct.setProductNum((long) num);
        cartProduct.setChecked("1");
        jedisUtil.hset(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(itemId), JSON.toJSONString(cartProduct));
        return 1;
    }

    public List<CartProduct> getCartList(long userId) {

        List<String> jsonList = jedisUtil.hvals(RedisKeyEnum.CART_PRE.getKey() + userId);
        List<CartProduct> list = new ArrayList<>();
        for (String json : jsonList) {
            CartProduct cartProduct = JSON.parseObject(json, CartProduct.class);
            list.add(cartProduct);
        }
        return list;
    }

    public int updateCartNum(long userId, long itemId, int num, String checked) {

        String json = jedisUtil.hget(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(itemId));
        if (json == null) {
            return 0;
        }
        CartProduct cartProduct = JSON.parseObject(json, CartProduct.class);
        cartProduct.setProductNum((long) num);
        cartProduct.setChecked(checked);
        jedisUtil.hset(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(itemId), JSON.toJSONString(cartProduct));
        return 1;
    }

    public int checkAll(long userId, String checked) {

        List<String> jsonList = jedisUtil.hvals(RedisKeyEnum.CART_PRE.getKey() + userId);

        for (String json : jsonList) {
            CartProduct cartProduct = JSON.parseObject(json, CartProduct.class);
            if ("true".equals(checked)) {
                cartProduct.setChecked("1");
            } else if ("false".equals(checked)) {
                cartProduct.setChecked("0");
            } else {
                return 0;
            }
            jedisUtil.hset(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(cartProduct.getProductId()), JSON.toJSONString(cartProduct));
        }

        return 1;
    }

    public int deleteCartItem(long userId, long itemId) {

        jedisUtil.hdel(RedisKeyEnum.CART_PRE.getKey() + userId, String.valueOf(itemId));
        return 1;
    }

    public int delChecked(long userId) {

        List<String> jsonList = jedisUtil.hvals(RedisKeyEnum.CART_PRE.getKey() + userId);
        List<String> delCartProducts = new ArrayList<String>();
        for (String json : jsonList) {
            CartProduct cartProduct = JSON.parseObject(json, CartProduct.class);
            if ("1".equals(cartProduct.getChecked())) {
                delCartProducts.add(String.valueOf(cartProduct.getProductId()));
            }
        }
        jedisUtil.hdel(RedisKeyEnum.CART_PRE.getKey() + userId, (String[]) delCartProducts.toArray());
        return 1;
    }
}
