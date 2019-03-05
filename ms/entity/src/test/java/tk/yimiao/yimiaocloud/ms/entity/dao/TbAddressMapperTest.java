package tk.yimiao.yimiaocloud.ms.entity.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.yimiao.yimiaocloud.ms.entity.domain.TbAddress;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * @author yimiao
 * @version V1.0
 * @Package tk.yimiao.yimiaocloud.ms.entity.dao
 * @Description: TODO
 * @date 2019-03-05 13:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbAddressMapperTest {


    @Autowired
    DataSource dataSource;
    @Autowired
    TbAddressMapper tbAddressMapper;

    @Test
    public void test(){
        try {
            Connection connection = dataSource.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            assertFalse(true);
        }
        TbAddress tbAddress = new TbAddress();
        tbAddress.setAddress("test");
        tbAddress.setAlias("test");
        tbAddressMapper.insert(tbAddress);
        TbAddress tbAddress1 = tbAddressMapper.selectByPrimaryKey(tbAddress.getId());
        assertNotNull(tbAddress1.getAddress());
        assertEquals(tbAddress.getAddress(),tbAddress1.getAddress());
        tbAddressMapper.deleteByPrimaryKey(tbAddress.getId());

    }


}