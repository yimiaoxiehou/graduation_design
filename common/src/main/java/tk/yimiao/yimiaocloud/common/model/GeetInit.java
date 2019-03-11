package tk.yimiao.yimiaocloud.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeetInit implements Serializable {

    private int success;

    private String challenge;

    private String gt;

    private String statusKey;
}
