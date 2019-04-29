package tk.yimiao.yimiaocloud.microservice.mall.base.dto;

import lombok.Data;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbAddress;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbOrder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order implements Serializable {

    private String orderId;

    private BigDecimal orderTotal;

    private TbAddress addressInfo;

    private List<CartProduct> goodsList = new ArrayList<CartProduct>();

    private String orderStatus;

    private String createDate;

    private String closeDate;

    private String finishDate;

    private String payDate;

    public Order() {
    }

    public static class Builder {
        Order order;

        public Builder(TbOrder tbOrder) {
            order = new Order();
            order.setOrderId(tbOrder.getOrderId());
            order.setOrderTotal(tbOrder.getPayment() != null ? tbOrder.getPayment() : BigDecimal.ZERO);
            order.setOrderStatus(String.valueOf(tbOrder.getStatus()));
        }

        public Builder createDate(String date) {
            order.setCreateDate(date);
            return this;
        }

        public Builder closeDate(String date) {
            order.setCloseDate(date);
            return this;
        }

        public Builder payDate(String date) {
            order.setPayDate(date);
            return this;
        }

        public Builder finishDate(String date) {
            order.setFinishDate(date);
            return this;
        }

        public Builder address(TbAddress tbAddress) {
            order.addressInfo = tbAddress;
            return this;
        }

        public Builder goodList(List<CartProduct> goodList) {
            order.goodsList.addAll(goodList);
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
