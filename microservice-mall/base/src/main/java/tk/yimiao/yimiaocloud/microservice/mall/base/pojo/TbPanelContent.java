package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TbPanelContent {
    private Integer id;

    private Integer panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updated;

    /**
     * 关联商品信息
     */
    private BigDecimal salePrice;

    private String productName;

    private String subTitle;

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl == null ? null : fullUrl.trim();
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public void setPicUrl2(String picUrl2) {
        this.picUrl2 = picUrl2 == null ? null : picUrl2.trim();
    }

    public void setPicUrl3(String picUrl3) {
        this.picUrl3 = picUrl3 == null ? null : picUrl3.trim();
    }

}