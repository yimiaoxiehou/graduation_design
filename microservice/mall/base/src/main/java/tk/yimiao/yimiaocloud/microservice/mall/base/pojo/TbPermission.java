package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;


import lombok.Data;

@Data
public class TbPermission {
    private Integer id;

    private String name;

    private String permission;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }
}