/**
 * @Package tk.yimiao.yimiaocloud.common.model
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-28 21:16
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.model;

import lombok.Data;

import java.util.List;

@Data
public class IpWeatherResult {

    String msg;

    List<City> result;
}
