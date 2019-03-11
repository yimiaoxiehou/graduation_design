package tk.yimiao.yimiaocloud.microservice.mall.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbAddressMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbAddress;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author yimiao
 * @version V1.0
 * @Package tk.yimiao.yimiaocloud.microservice.mall.base.mapper
 * @Description: TODO
 * @date 2019-03-06 22:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbAddressMapperTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    TbAddressMapper tbAddressMapper;

    @Test
    public void test() {
        try {
            Connection connection = dataSource.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            assertFalse(true);
        }
        TbAddress tbAddress = new TbAddress();
        tbAddress.setUserName("qhuwegwqjh");
        tbAddress.setStreetName("yquweghdwqiu");
        tbAddressMapper.insert(tbAddress);
        System.out.println(tbAddress.getAddressId());
    }
}