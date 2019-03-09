package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TbItem {
    private Long id;

    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String image;

    private Long cid;

    private Integer status;

    private Date created;

    private Date updated;

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }


    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }


    public String[] getImages() {
        if (image != null && !"".equals(image)) {
            String[] strings = image.split(",");
            return strings;
        }
        return null;
    }
}