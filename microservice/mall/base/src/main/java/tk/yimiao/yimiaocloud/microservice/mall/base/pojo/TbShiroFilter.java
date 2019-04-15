package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;


import lombok.Data;

@Data
public class TbShiroFilter {
    private Integer id;

    private String name;

    private String perms;

    private Integer sortOrder;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }
}