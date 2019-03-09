/**
 * @Package tk.yimiao.yimiaocloud.common.util
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-08 18:03
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.common.util;

import java.util.UUID;

public class UUIDGenerator {

    public static String getUUID(){
        StringBuilder sb = new StringBuilder();
        synchronized (sb){
            UUID uuid = UUID.randomUUID();
            String[] list = uuid.toString().split("-");
            for (String s:list){
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
