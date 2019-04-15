/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.manager.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-28 20:46
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.ZTreeNode;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.DtoUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbPanelMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbPanel;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbPanelExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PanelService {

    @Autowired
    private TbPanelMapper tbPanelMapper;
    @Autowired
    private JedisUtil jedisUtil;

    // fixme
//    @Value("${PRODUCT_HOME}")
    @Value("/")
    private String PRODUCT_HOME;


    public TbPanel getTbPanelById(int id) {

        TbPanel tbPanel = tbPanelMapper.selectByPrimaryKey(id);
        if (tbPanel == null) {
            throw new BusinessException("通过id获得板块失败");
        }
        return tbPanel;
    }


    public List<ZTreeNode> getPanelList(int position, boolean showAll) {

        TbPanelExample example = new TbPanelExample();
        TbPanelExample.Criteria criteria = example.createCriteria();
        if (position == 0 && !showAll) {
            //除去非轮播
            criteria.andTypeNotEqualTo(0);
        } else if (position == -1) {
            //仅含轮播
            position = 0;
            criteria.andTypeEqualTo(0);
        }
        //首页板块
        criteria.andPositionEqualTo(position);
        example.setOrderByClause("sort_order");
        List<TbPanel> panelList = tbPanelMapper.selectByExample(example);

        List<ZTreeNode> list = new ArrayList<>();

        for (TbPanel tbPanel : panelList) {
            ZTreeNode zTreeNode = DtoUtil.TbPanel2ZTreeNode(tbPanel);
            list.add(zTreeNode);
        }

        return list;
    }


    public int addPanel(TbPanel tbPanel) {

        if (tbPanel.getType() == 0) {
            TbPanelExample example = new TbPanelExample();
            TbPanelExample.Criteria criteria = example.createCriteria();
            criteria.andTypeEqualTo(0);
            List<TbPanel> list = tbPanelMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                throw new BusinessException("已有轮播图板块,轮播图仅能添加1个!");
            }
        }

        tbPanel.setCreated(new Date());
        tbPanel.setUpdated(new Date());

        if (tbPanelMapper.insert(tbPanel) != 1) {
            throw new BusinessException("添加板块失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }


    public int updatePanel(TbPanel tbPanel) {

        TbPanel old = getTbPanelById(tbPanel.getId());
        tbPanel.setUpdated(new Date());

        if (tbPanelMapper.updateByPrimaryKey(tbPanel) != 1) {
            throw new BusinessException("更新板块失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }


    public int deletePanel(int id) {

        if (tbPanelMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除内容分类失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    /**
     * 同步首页缓存
     */
    public void deleteHomeRedis() {
        try {
            jedisUtil.del(PRODUCT_HOME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
