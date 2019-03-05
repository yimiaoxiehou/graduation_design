/**
 * @Package tk.yimiao.yimiaocloud.ms.service.user
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-05 15:46
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.ms.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.ms.entity.dao.TbUserMapper;

@Service
public class LoginService {

    @Autowired
    TbUserMapper tbUserMapper;



}
