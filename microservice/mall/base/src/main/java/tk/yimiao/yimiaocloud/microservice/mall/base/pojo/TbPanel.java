package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TbPanel {
    private Integer id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private Integer limitNum;

    private Integer status;

    private String remark;

    private Date created;

    private Date updated;

    private List<TbPanelContent> panelContents;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}