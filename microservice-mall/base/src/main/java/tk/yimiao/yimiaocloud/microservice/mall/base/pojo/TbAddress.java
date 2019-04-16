package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;


import lombok.Data;

@Data

public class TbAddress {
    private Long addressId;

    private Long userId;

    private String userName;

    private String tel;

    private String streetName;

    private Boolean isDefault;

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName == null ? null : streetName.trim();
    }

}