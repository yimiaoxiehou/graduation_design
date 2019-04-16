/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.manager.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-12 18:31
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.ZTreeNode;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.DtoUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbItemCatMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbItemCat;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbItemCatExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;


    public TbItemCat getItemCatById(Long id) {

        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(id);
        if (tbItemCat == null) {
            throw new BusinessException("通过id获取商品分类失败");
        }
        return tbItemCat;
    }


    public List<ZTreeNode> getItemCatList(int parentId) {

        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //排序
        example.setOrderByClause("sort_order");
        //条件查询
        criteria.andParentIdEqualTo(new Long(parentId));
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);

        //转换成ZtreeNode
        List<ZTreeNode> resultList = new ArrayList<>();

        for (TbItemCat tbItemCat : list) {

            ZTreeNode node = DtoUtil.TbItemCat2ZTreeNode(tbItemCat);

            resultList.add(node);
        }

        return resultList;
    }


    public int addItemCat(TbItemCat tbItemCat) {

        if (tbItemCat.getParentId() == 0) {
            //根节点
            tbItemCat.setSortOrder(0);
            tbItemCat.setStatus(1);
        } else {
            TbItemCat parent = tbItemCatMapper.selectByPrimaryKey(tbItemCat.getParentId());
            tbItemCat.setSortOrder(0);
            tbItemCat.setStatus(1);
            tbItemCat.setCreated(new Date());
            tbItemCat.setUpdated(new Date());
        }


        if (tbItemCatMapper.insert(tbItemCat) != 1) {
            throw new BusinessException("添加商品分类失败");
        }
        return 1;
    }


    public int updateItemCat(TbItemCat tbItemCat) {

        TbItemCat old = getItemCatById(tbItemCat.getId());
        tbItemCat.setCreated(old.getCreated());
        tbItemCat.setUpdated(new Date());

        if (tbItemCatMapper.updateByPrimaryKey(tbItemCat) != 1) {
            throw new BusinessException("添加商品分类失败");
        }
        return 1;
    }


    public void deleteItemCat(Long id) {

        deleteZTree(id);
    }


    public void deleteZTree(Long id) {

        //查询该节点所有子节点
        List<ZTreeNode> node = getItemCatList(Math.toIntExact(id));
        if (node.size() > 0) {
            //如果有子节点，遍历子节点
            for (int i = 0; i < node.size(); i++) {
                deleteItemCat((long) node.get(i).getId());
            }
        }
        //没有子节点直接删除
        if (tbItemCatMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除商品分类失败");
        }
    }

}
