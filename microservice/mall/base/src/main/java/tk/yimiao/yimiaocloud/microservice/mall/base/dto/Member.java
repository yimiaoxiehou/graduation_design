package tk.yimiao.yimiaocloud.microservice.mall.base.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String sex;

    private String address;

    private String file;

    private String description;

    private Integer points;

    private Long balance;

    private int state;

    private String token;

    private String message;

}
