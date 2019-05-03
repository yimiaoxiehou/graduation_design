/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-07 22:32
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.base.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.constant.ConstField;
import tk.yimiao.yimiaocloud.common.constant.EmailSubjectEnum;
import tk.yimiao.yimiaocloud.common.constant.GlobalErrorCodeEnum;
import tk.yimiao.yimiaocloud.common.constant.RedisKeyEnum;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.common.util.DateUtil;
import tk.yimiao.yimiaocloud.common.util.EmailUtil;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.common.util.UUIDGenerator;
import tk.yimiao.yimiaocloud.microservice.mall.base.constant.OrderStateEnum;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.*;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.*;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbMemberMapper tbMemberMapper;
    @Autowired
    private TbThanksMapper tbThanksMapper;
    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 查询订单列表，使用 pagehelper 封装
     *
     * @param userId 用户id
     * @param page   页码
     * @param size   数量
     * @return
     */
    public PageOrder getOrderList(Long userId, int page, int size) {

        if (page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page, size);

        PageOrder pageOrder = new PageOrder();

        // 查询订单
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria().andUserIdEqualTo(userId);
        tbOrderExample.setOrderByClause("create_time desc");

        // 从 pojo 类封装订单信息到 dto
        List<TbOrder> tbOrders = tbOrderMapper.selectByExample(tbOrderExample);
        List<Order> orders = new ArrayList<Order>();
        tbOrders.forEach(
                o -> {
                    // 根据 orderId 查询 order
                    Order order = getOrder(o.getOrderId());
                    if (order != null) {
                        orders.add(order);
                    }
                }
        );

        // 设置 pagehelper 控制的信息
        PageInfo<Order> pageInfo = new PageInfo<>(orders);

        pageOrder.setTotal(getMemberOrderCount(userId));
        pageOrder.setData(orders);
        return pageOrder;

    }

    /**
     * 查询用户的订单数量
     *
     * @param userId
     * @return
     */
    private int getMemberOrderCount(Long userId) {
        TbOrderExample example = new TbOrderExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<TbOrder> orders = tbOrderMapper.selectByExample(example);
        if (null == orders) {
            return 0;
        }
        return orders.size();
    }

    /**
     * 根据 orderId 查询 order
     *
     * @param orderId
     * @return
     */
    public Order getOrder(String orderId) {

        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);

        // 查询为空，则抛出自定义错误
        if (tbOrder == null) {
            log.error(GlobalErrorCodeEnum.BUSINESS_ORDER_NOT_FOUND.getMessage() + String.format(" order id : {%s}", orderId));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ORDER_NOT_FOUND);
        }

        // 订单状态不正常就跳过当前订单
        if (!OrderStateEnum.isNormalState(tbOrder.getStatus())) {
            return null;
        }

        // 查询订单收货信息
        TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(tbOrder.getOrderId());

        TbAddress tbAddress = new TbAddress();
        if(tbOrderShipping!=null) {
            tbAddress.setUserName(tbOrderShipping.getReceiverName());
            tbAddress.setStreetName(tbOrderShipping.getReceiverAddress());
            tbAddress.setTel(tbOrderShipping.getReceiverMobile());
        }

        // 查询订单商品列表
        TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
        tbOrderItemExample.createCriteria().andOrderIdEqualTo(tbOrder.getOrderId());
        List<TbOrderItem> tbOrderItems = tbOrderItemMapper.selectByExample(tbOrderItemExample);

        // 从 pojo 封装商品信息到 dto
        List<CartProduct> cartProducts = new ArrayList<>();
        tbOrderItems.forEach(
                i -> {
                    CartProduct cartProduct = DtoUtil.TbOrderItem2CartProduct(i);
                    cartProducts.add(cartProduct);
                }
        );

        String createDate = DateUtil.format(tbOrder.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM);
        String paymentTime = DateUtil.format(tbOrder.getPaymentTime(), DateUtil.YYYY_MM_DD_HH_MM);
        String closeTime = DateUtil.format(tbOrder.getCloseTime(), DateUtil.YYYY_MM_DD_HH_MM);
        String endTime = DateUtil.format(tbOrder.getEndTime(), DateUtil.YYYY_MM_DD_HH_MM);

        // 用 builder 模式 构建订单对象
        Order order = new Order.Builder(tbOrder)
                .address(tbAddress)
                .createDate(createDate)
                .payDate(paymentTime)
                .finishDate(endTime)
                .closeDate(closeTime)
                .goodList(cartProducts)
                .build();

        return order;
    }

    /**
     * 创建订单
     *
     * @param orderInfo 订单信息
     * @return
     */
    public String createOrder(OrderInfo orderInfo) {

        TbMember member = tbMemberMapper.selectByPrimaryKey(Long.valueOf(orderInfo.getUserId()));
        if (null == member) {
            log.error(GlobalErrorCodeEnum.USER_NOT_FOUND.getMessage() + String.format(" user id : {%s}", orderInfo.getUserId()));
            throw new BusinessException(GlobalErrorCodeEnum.USER_NOT_FOUND);
        }

        TbOrder tbOrder = new TbOrder();
        Date date = new Date();

        String orderId = UUIDGenerator.getUUID();
        tbOrder.setOrderId(orderId);
        tbOrder.setUserId(Long.valueOf(orderInfo.getUserId()));
        tbOrder.setBuyerNick(member.getUsername());
        tbOrder.setPayment(orderInfo.getOrderTotal());
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        tbOrder.setStatus(OrderStateEnum.CREATE.getCode());

        if (tbOrderMapper.insert(tbOrder) != 1) {
            log.error(GlobalErrorCodeEnum.BUSINESS_ORDER_NOT_FOUND.getMessage() + String.format(" order is : {%s}", JSON.toJSONString(tbOrder)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ORDER_CREATE_FAIL);
        }

        List<CartProduct> cartProducts = orderInfo.getGoodsList();
        for (CartProduct cartProduct : cartProducts) {
            TbOrderItem tbOrderItem = new TbOrderItem();

            String orderItemId = UUIDGenerator.getUUID();
            tbOrderItem.setId(orderItemId);
            tbOrderItem.setItemId(String.valueOf(cartProduct.getProductId()));
            tbOrderItem.setOrderId(orderId);
            tbOrderItem.setNum(Math.toIntExact(cartProduct.getProductNum()));
            tbOrderItem.setPicPath(cartProduct.getProductImg());
            tbOrderItem.setPrice(cartProduct.getSalePrice());
            tbOrderItem.setTotalFee(cartProduct.getSalePrice().multiply(BigDecimal.valueOf(cartProduct.getProductNum())));

            if (tbOrderItemMapper.insert(tbOrderItem) != 1) {
                log.error(GlobalErrorCodeEnum.BUSINESS_ORDER_ITEM_CREATE_FAIL.getMessage() + String.format(" order is : {%s}", JSON.toJSONString(tbOrder)));
                throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ORDER_ITEM_CREATE_FAIL);
            }

            // 删除购物车商品

            try {
                String key = RedisKeyEnum.CART_PRE.getKey() + orderInfo.getUserId();
                List<String> jsonList = jedisUtil.hvals(key);
                List<String> delCartItem = new ArrayList<String>();
                for (String s : jsonList) {
                    CartProduct cart = JSON.parseObject(s, CartProduct.class);
                    if (cart.getProductId().equals(cartProduct.getProductId())) {
                        jedisUtil.hdel(key, cartProduct.getProductId()+"");
                    }
                }
            } catch (Exception e) {
                log.error("", e);
                log.error(GlobalErrorCodeEnum.BUSINESS_ORDER_ITEM_CREATE_FAIL.getMessage() + String.format(" userId is : {%s}", orderInfo.getUserId()));
                throw new BusinessException(e.getCause(), GlobalErrorCodeEnum.BUSINESS_ORDER_ITEM_CREATE_FAIL);
            }
        }

        TbOrderShipping tbOrderShipping = new TbOrderShipping();
        tbOrderShipping.setOrderId(orderId);
        tbOrderShipping.setReceiverName(orderInfo.getUserName());
        tbOrderShipping.setReceiverAddress(orderInfo.getStreetName());
        tbOrderShipping.setReceiverPhone(orderInfo.getTel());
        tbOrderShipping.setCreated(date);
        tbOrderShipping.setUpdated(date);

        if (tbOrderShippingMapper.insert(tbOrderShipping) != 1) {
            log.error(GlobalErrorCodeEnum.BUSINESS_SHIPPING_SAVE_FAIL.getMessage() + String.format(" ship info is : {%s}", JSON.toJSONString(tbOrderShipping)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_SHIPPING_SAVE_FAIL);
        }

        return orderId;
    }

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @return
     */
    public int cancelOrder(String orderId) {
        return updateOrderState(orderId, OrderStateEnum.CANCEL);
    }

    /**
     * 删除订单
     *
     * @param orderId 订单id
     * @return
     */
    public int delOrder(String orderId) {
        return updateOrderState(orderId, OrderStateEnum.DELETE);
    }

    /**
     * 更新订单状态
     *
     * @param orderId        订单id
     * @param orderStateEnum 状态 enum
     * @return
     */
    private int updateOrderState(String orderId, OrderStateEnum orderStateEnum) {

        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        if (tbOrder == null) {
            log.error(GlobalErrorCodeEnum.BUSINESS_ORDER_NOT_FOUND.getMessage() + String.format(" order id : {%s}", orderId));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ORDER_NOT_FOUND);
        }
        tbOrder.setStatus(orderStateEnum.getCode());
        tbOrder.setUpdateTime(new Date());
        tbOrder.setCloseTime(new Date());
        if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
            log.error(GlobalErrorCodeEnum.BUSINESS_ORDER_UPDATE_ERROR.getMessage() + String.format(". Order : {%s}", JSON.toJSONString(tbOrder)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ORDER_UPDATE_ERROR);
        }

        return 1;
    }

    /**
     * 订单支付
     *
     * @param tbThanks 支付信息
     */
    public void payOrder(TbThanks tbThanks) {

        Date date = new Date();
        String dateTime = new SimpleDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS).format(date);

        tbThanks.setTime(dateTime);
        tbThanks.setDate(date);

        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(Long.valueOf(tbThanks.getUserId()));
        if (tbMember != null) {
            tbThanks.setUsername(tbMember.getUsername());
        }
        if (tbThanksMapper.insert(tbThanks) != 1) {
            log.error(GlobalErrorCodeEnum.BUSINESS_PAY_INFO_SAVE_ERROR.getMessage() + String.format(". PayInfo : {%s}", JSON.toJSONString(tbThanks)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_PAY_INFO_SAVE_ERROR);
        }

        // 更新订单状态和支付时间
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        tbOrder.setStatus(OrderStateEnum.PAY.getCode());
        tbOrder.setUpdateTime(date);
        tbOrder.setPaymentTime(date);
        if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
            log.error(GlobalErrorCodeEnum.BUSINESS_ORDER_UPDATE_ERROR.getMessage() + String.format(". Order : {%s}", JSON.toJSONString(tbOrder)));
            throw new BusinessException(GlobalErrorCodeEnum.BUSINESS_ORDER_UPDATE_ERROR);
        }

        // 发送邮件确认
        String emailAddress = tbThanks.getEmail();
        String content = String.format("「 %s 」支付待审核确认，订单号：【%s】，支付金额：【%s】，支付方式：【%s】 ", ConstField.SYSTEM_NAME.getField(), tbThanks.getOrderId(), tbThanks.getMoney(), tbThanks.getPayType());
        EmailUtil.sendEmail(emailAddress, content, EmailSubjectEnum.PAY_CHECK);

    }


    public int deleteOrder(String id) {

        if (tbOrderMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除订单数失败");
        }

        TbOrderItemExample example = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(id);
        List<TbOrderItem> list = tbOrderItemMapper.selectByExample(example);
        for (TbOrderItem tbOrderItem : list) {
            if (tbOrderItemMapper.deleteByPrimaryKey(tbOrderItem.getId()) != 1) {
                throw new BusinessException("删除订单商品失败");
            }
        }

        if (tbOrderShippingMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除物流失败");
        }
        return 1;
    }


    public int cancelOrder() {

        TbOrderExample example = new TbOrderExample();
        List<TbOrder> list = tbOrderMapper.selectByExample(example);
        for (TbOrder tbOrder : list) {
            judgeOrder(tbOrder);
        }
        return 1;
    }

    /**
     * 判断订单是否超时未支付
     */
    public String judgeOrder(TbOrder tbOrder) {

        String result = null;
        if (tbOrder.getStatus() == 0) {
            //判断是否已超1天
            long diff = System.currentTimeMillis() - tbOrder.getCreateTime().getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days >= 1) {
                //设置失效
                tbOrder.setStatus(OrderStateEnum.CANCEL.getCode());
                tbOrder.setCloseTime(new Date());
                if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                    throw new BusinessException("设置订单关闭失败");
                }
            } else {
                //返回到期时间
                long time = tbOrder.getCreateTime().getTime() + 1000 * 60 * 60 * 24;
                result = String.valueOf(time);
            }
        }
        return result;
    }

    public int passPay(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisUtil.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //展示捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        tbThanks.setState(1);
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(OrderStateEnum.FINISH.getCode());
            tbOrder.setEndTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        //发送通知邮箱
        if (StringUtils.isNotBlank(tbThanks.getEmail()) && EmailUtil.checkEmail(tbThanks.getEmail())) {
            String content = "您的订单已支付成功，十分感谢您的捐赠！<br>您可以在捐赠名单中查看到您的数据：" +
                    "<a href='http://xmall.exrick.cn/#/thanks'>http://xmall.exrick.cn/#/thanks</a><br>Powered By XPay. Exrick Present.";
            EmailUtil.sendEmail(tbThanks.getEmail(), content, EmailSubjectEnum.PAY_CHECK);
        }
        return 1;
    }


    public int backPay(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisUtil.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //展示捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        tbThanks.setState(2);
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(6);
            tbOrder.setCloseTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        //发送通知邮箱
        if (StringUtils.isNotBlank(tbThanks.getEmail()) && EmailUtil.checkEmail(tbThanks.getEmail())) {
            String content = "抱歉，由于您支付不起或其他原因，您的订单支付失败，请尝试重新支付！<br>Powered By XPay. Exrick Present.";
            EmailUtil.sendEmail(tbThanks.getEmail(), content, EmailSubjectEnum.PAY_FAIL);
        }
        return 1;
    }


    public int notShowPay(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisUtil.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //展示捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        tbThanks.setState(3);
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(OrderStateEnum.FINISH.getCode());
            tbOrder.setEndTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        //发送通知邮箱
        if (StringUtils.isNotBlank(tbThanks.getEmail()) && EmailUtil.checkEmail(tbThanks.getEmail())) {
            String content = "您的订单已支付成功，十分感谢您的捐赠！<br>但由于您的支付金额过低或其他原因，将不会在捐赠名单中显示，敬请谅解！" +
                    "<br>Powered By XPay. Exrick Present.";
            EmailUtil.sendEmail(tbThanks.getEmail(), content, EmailSubjectEnum.PAY_CHECK);
        }
        return 1;
    }


    public int editPay(String tokenName, String token, TbThanks tbThanks) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(tbThanks.getId().toString())) {
            return -1;
        }
        String value = jedisUtil.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //保存
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(tbThanks.getTime());
            tbThanks.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        return 1;
    }


    public int payDelNotNotify(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisUtil.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //获得捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        //删除捐赠
        if (tbThanksMapper.deleteByPrimaryKey(Integer.valueOf(id)) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(6);
            tbOrder.setCloseTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        return 1;
    }

    public int payDel(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisUtil.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //获得捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        //删除捐赠
        if (tbThanksMapper.deleteByPrimaryKey(Integer.valueOf(id)) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(6);
            tbOrder.setCloseTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        //发送通知邮箱
        if (StringUtils.isNotBlank(tbThanks.getEmail()) && EmailUtil.checkEmail(tbThanks.getEmail())) {
            String content = "抱歉，由于您支付不起或其他原因，您的订单支付失败，请尝试重新支付！<br>Powered By XPay. Exrick Present.";
            EmailUtil.sendEmail(tbThanks.getEmail(), content, EmailSubjectEnum.PAY_FAIL);
        }
        return 1;
    }

    public DataTablesResult getOrderList(int draw, int start, int length, String search, String orderCol, String orderDir) {

        DataTablesResult result = new DataTablesResult();
        //分页
        PageHelper.startPage(start / length + 1, length);
        List<TbOrder> list = tbOrderMapper.selectByMulti("%" + search + "%", orderCol, orderDir);
        PageInfo<TbOrder> pageInfo = new PageInfo<>(list);

        result.setRecordsFiltered((int) pageInfo.getTotal());
        result.setRecordsTotal(Math.toIntExact(cancelOrder()));

        result.setDraw(draw);
        result.setData(list);
        return result;
    }

    public Long countOrder() {

        TbOrderExample example = new TbOrderExample();
        Long result = tbOrderMapper.countByExample(example);
        if (result == null) {
            throw new BusinessException("统计订单数目失败");
        }
        return result;
    }


    public OrderDetail getOrderDetail(String orderId) {

        OrderDetail orderDetail = new OrderDetail();
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);

        TbOrderItemExample example = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<TbOrderItem> orderItemList = tbOrderItemMapper.selectByExample(example);

        TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(orderId);

        orderDetail.setTbOrder(tbOrder);
        orderDetail.setTbOrderItem(orderItemList);
        orderDetail.setTbOrderShipping(tbOrderShipping);
        return orderDetail;
    }

    public int remark(String orderId, String message) {

        TbOrder o = tbOrderMapper.selectByPrimaryKey(orderId);
        o.setBuyerMessage(message);
        o.setUpdateTime(new Date());
        tbOrderMapper.updateByPrimaryKey(o);
        return 1;
    }

    public int deliver(String orderId, String shippingName, String shippingCode, BigDecimal postFee) {

        TbOrder o = tbOrderMapper.selectByPrimaryKey(orderId);
        o.setShippingName(shippingName);
        o.setShippingCode(shippingCode);
        o.setPostFee(postFee);
        o.setConsignTime(new Date());
        o.setUpdateTime(new Date());
        // 0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭
        o.setStatus(OrderStateEnum.SHIPPING.getCode());
        tbOrderMapper.updateByPrimaryKey(o);
        return 1;
    }

    public int cancelOrderByAdmin(String orderId) {

        TbOrder o = tbOrderMapper.selectByPrimaryKey(orderId);
        o.setCloseTime(new Date());
        o.setUpdateTime(new Date());
        // 0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭
        o.setStatus(OrderStateEnum.CANCEL.getCode());
        tbOrderMapper.updateByPrimaryKey(o);
        return 1;
    }
}
