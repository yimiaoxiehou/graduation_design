package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;


import lombok.Data;

@Data
public class TbRole {
    private Integer id;

    private String name;

    private String description;

    private Integer[] roles;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

}