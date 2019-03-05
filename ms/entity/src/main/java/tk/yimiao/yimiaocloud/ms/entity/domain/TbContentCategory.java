package tk.yimiao.yimiaocloud.ms.entity.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Package tk.yimiao.yimiaocloud.common.base.domain
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-02 22:41
 * @version V1.0
 */

@Table(name = "tb_content_category")
public class TbContentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}