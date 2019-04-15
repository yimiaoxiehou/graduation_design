/**
 * @Package tk.yimiao.yimiaocloud.common.model
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-28 21:14
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpInfo implements Serializable {

    String url;

    String p;
}