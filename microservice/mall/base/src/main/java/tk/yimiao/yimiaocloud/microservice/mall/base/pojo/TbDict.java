package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;


import lombok.Data;

@Data
public class TbDict {
    private Integer id;

    private String dict;

    private Integer type;

    public void setDict(String dict) {
        this.dict = dict == null ? null : dict.trim();
    }

}