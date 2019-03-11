/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.front.service
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-10 22:43
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.front.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.reflect.TypeToken;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.yimiao.yimiaocloud.common.constant.RedisKeyEnum;
import tk.yimiao.yimiaocloud.common.exception.BusinessException;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.common.util.JedisUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.AllGoodsResult;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.DtoUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.Product;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.ProductDet;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbItemDescMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbItemMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbPanelContentMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.mapper.TbPanelMapper;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ContentService {
    @Autowired
    private TbPanelMapper tbPanelMapper;
    @Autowired
    private TbPanelContentMapper tbPanelContentMapper;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JedisUtil jedisUtil;
    @Value("1")
    private Integer RECOMEED_PANEL_ID;
    @Value("1")
    private Integer THANK_PANEL_ID;
    @Value("1800")
    private int ITEM_EXPIRE;
    @Value("0")
    private int HEADER_PANEL_ID;


    public int addPanelContent(TbPanelContent tbPanelContent) {

        tbPanelContent.setCreated(new Date());
        tbPanelContent.setUpdated(new Date());
        if (tbPanelContentMapper.insert(tbPanelContent) != 1) {
            throw new BusinessException("添加首页板块内容失败");
        }
        //同步导航栏缓存
        if (tbPanelContent.getPanelId() == HEADER_PANEL_ID) {
            updateNavListRedis();
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    public DataTablesResult getPanelContentListByPanelId(int panelId) {

        DataTablesResult result = new DataTablesResult();
        List<TbPanelContent> list = new ArrayList<>();

        TbPanelContentExample example = new TbPanelContentExample();
        TbPanelContentExample.Criteria criteria = example.createCriteria();
        //条件查询
        criteria.andPanelIdEqualTo(panelId);
        list = tbPanelContentMapper.selectByExample(example);
        for (TbPanelContent content : list) {
            if (content.getProductId() != null) {
                TbItem tbItem = tbItemMapper.selectByPrimaryKey(content.getProductId());
                content.setProductName(tbItem.getTitle());
                content.setSalePrice(tbItem.getPrice());
                content.setSubTitle(tbItem.getSellPoint());
            }
        }

        result.setData(list);
        return result;
    }


    public int deletePanelContent(int id) {

        if (tbPanelContentMapper.deleteByPrimaryKey(id) != 1) {
            throw new BusinessException("删除首页板块失败");
        }
        //同步导航栏缓存
        if (id == HEADER_PANEL_ID) {
            updateNavListRedis();
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }


    public int updateContent(TbPanelContent tbPanelContent) {

        TbPanelContent old = getTbPanelContentById(tbPanelContent.getId());
        if (StringUtils.isBlank(tbPanelContent.getPicUrl())) {
            tbPanelContent.setPicUrl(old.getPicUrl());
        }
        if (StringUtils.isBlank(tbPanelContent.getPicUrl2())) {
            tbPanelContent.setPicUrl2(old.getPicUrl2());
        }
        if (StringUtils.isBlank(tbPanelContent.getPicUrl3())) {
            tbPanelContent.setPicUrl3(old.getPicUrl3());
        }
        tbPanelContent.setCreated(old.getCreated());
        tbPanelContent.setUpdated(new Date());
        if (tbPanelContentMapper.updateByPrimaryKey(tbPanelContent) != 1) {
            throw new BusinessException("更新板块内容失败");
        }
        //同步导航栏缓存
        if (tbPanelContent.getPanelId() == HEADER_PANEL_ID) {
            updateNavListRedis();
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }


    public TbPanelContent getTbPanelContentById(int id) {

        TbPanelContent tbPanelContent = tbPanelContentMapper.selectByPrimaryKey(id);
        if (tbPanelContent == null) {
            throw new BusinessException("通过id获取板块内容失败");
        }
        return tbPanelContent;
    }


    public List<TbPanel> getHome() {

        List<TbPanel> list = new ArrayList<>();

        //查询缓存
        try {
            //有缓存则读取
            String json = jedisUtil.get(RedisKeyEnum.PRODUCT_HOME.getKey());
            if (json != null) {
                list = JSON.parseObject(json, new TypeToken<List<TbPanel>>() {
                }.getType());
                log.info("读取了首页缓存");
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //没有缓存
        TbPanelExample example = new TbPanelExample();
        TbPanelExample.Criteria criteria = example.createCriteria();
        //条件查询
        criteria.andPositionEqualTo(0);
        criteria.andStatusEqualTo(1);
        example.setOrderByClause("sort_order");
        list = tbPanelMapper.selectByExample(example);
        for (TbPanel tbPanel : list) {
            TbPanelContentExample exampleContent = new TbPanelContentExample();
            exampleContent.setOrderByClause("sort_order");
            TbPanelContentExample.Criteria criteriaContent = exampleContent.createCriteria();
            //条件查询
            criteriaContent.andPanelIdEqualTo(tbPanel.getId());
            List<TbPanelContent> contentList = tbPanelContentMapper.selectByExample(exampleContent);
            for (TbPanelContent content : contentList) {
                if (content.getProductId() != null) {
                    TbItem tbItem = tbItemMapper.selectByPrimaryKey(content.getProductId());
                    content.setProductName(tbItem.getTitle());
                    content.setSalePrice(tbItem.getPrice());
                    content.setSubTitle(tbItem.getSellPoint());
                }
            }

            tbPanel.setPanelContents(contentList);
        }

        //把结果添加至缓存
        try {
            jedisUtil.set(RedisKeyEnum.PRODUCT_HOME.getKey(), JSON.toJSONString(list));
            log.info("添加了首页缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<TbPanel> getRecommendGoods() {


        List<TbPanel> list = new ArrayList<>();
        //查询缓存
        try {
            //有缓存则读取
            String json = jedisUtil.get(RedisKeyEnum.RECOMEED_PANEL.getKey());
            if (json != null) {
                list = JSON.parseObject(json, new TypeToken<List<TbPanel>>() {
                }.getType());
                log.info("读取了推荐板块缓存");
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = getTbPanelAndContentsById(RECOMEED_PANEL_ID);
        //把结果添加至缓存
        try {
            jedisUtil.set(RedisKeyEnum.RECOMEED_PANEL.getKey(), JSON.toJSONString(list));
            log.info("添加了推荐板块缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<TbPanel> getThankGoods() {

        List<TbPanel> list = new ArrayList<>();
        //查询缓存
        try {
            //有缓存则读取
            String json = jedisUtil.get(RedisKeyEnum.THANK_PANEL.getKey());
            if (json != null) {
                list = JSON.parseObject(json, new TypeToken<List<TbPanel>>() {
                }.getType());
                log.info("读取了捐赠板块缓存");
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = getTbPanelAndContentsById(THANK_PANEL_ID);
        //把结果添加至缓存
        try {
            jedisUtil.set(RedisKeyEnum.THANK_PANEL.getKey(), JSON.toJSONString(list));
            log.info("添加了捐赠板块缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    List<TbPanel> getTbPanelAndContentsById(Integer panelId) {

        List<TbPanel> list = new ArrayList<>();
        TbPanelExample example = new TbPanelExample();
        TbPanelExample.Criteria criteria = example.createCriteria();
        //条件查询
        criteria.andIdEqualTo(panelId);
        criteria.andStatusEqualTo(1);
        list = tbPanelMapper.selectByExample(example);
        for (TbPanel tbPanel : list) {
            TbPanelContentExample exampleContent = new TbPanelContentExample();
            exampleContent.setOrderByClause("sort_order");
            TbPanelContentExample.Criteria criteriaContent = exampleContent.createCriteria();
            //条件查询
            criteriaContent.andPanelIdEqualTo(tbPanel.getId());
            List<TbPanelContent> contentList = tbPanelContentMapper.selectByExample(exampleContent);
            for (TbPanelContent content : contentList) {
                if (content.getProductId() != null) {
                    TbItem tbItem = tbItemMapper.selectByPrimaryKey(content.getProductId());
                    content.setProductName(tbItem.getTitle());
                    content.setSalePrice(tbItem.getPrice());
                    content.setSubTitle(tbItem.getSellPoint());
                }
            }

            tbPanel.setPanelContents(contentList);
        }
        return list;
    }


    public ProductDet getProductDet(Long id) {

        //查询缓存
        try {
            //有缓存则读取
            String json = jedisUtil.get(RedisKeyEnum.PRODUCT_ITEM_PRE.getKey() + id);
            if (json != null) {
                ProductDet productDet = JSON.parseObject(json, ProductDet.class);
                log.info("读取了商品" + id + "详情缓存");
                //重置商品缓存时间
                jedisUtil.expire(RedisKeyEnum.PRODUCT_ITEM_PRE.getKey() + id, ITEM_EXPIRE);
                return productDet;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        ProductDet productDet = new ProductDet();
        productDet.setProductId(id);
        productDet.setProductName(tbItem.getTitle());
        productDet.setSubTitle(tbItem.getSellPoint());
        if (tbItem.getLimitNum() != null && !tbItem.getLimitNum().toString().isEmpty()) {
            productDet.setLimitNum(Long.valueOf(tbItem.getLimitNum()));
        } else {
            productDet.setLimitNum(Long.valueOf(tbItem.getNum()));
        }
        productDet.setSalePrice(tbItem.getPrice());

        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        productDet.setDetail(tbItemDesc.getItemDesc());

        if (tbItem.getImage() != null && !tbItem.getImage().isEmpty()) {
            String images[] = tbItem.getImage().split(",");
            productDet.setProductImageBig(images[0]);
            List list = new ArrayList();
            for (int i = 0; i < images.length; i++) {
                list.add(images[i]);
            }
            productDet.setProductImageSmall(list);
        }
        //无缓存 把结果添加至缓存
        try {
            jedisUtil.set(RedisKeyEnum.PRODUCT_ITEM_PRE.getKey() + id, JSON.toJSONString(productDet));
            //设置过期时间
            jedisUtil.expire(RedisKeyEnum.PRODUCT_ITEM_PRE.getKey() + id, ITEM_EXPIRE);
            log.info("添加了商品" + id + "详情缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDet;
    }


    public AllGoodsResult getAllProduct(int page, int size, String sort, Long cid, int priceGt, int priceLte) {

        AllGoodsResult allGoodsResult = new AllGoodsResult();
        List<Product> list = new ArrayList<>();
        //分页执行查询返回结果
        if (page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page, size);

        //判断条件
        String orderCol = "created";
        String orderDir = "desc";
        if (sort.equals("1")) {
            orderCol = "price";
            orderDir = "asc";
        } else if (sort.equals("-1")) {
            orderCol = "price";
            orderDir = "desc";
        } else {
            orderCol = "created";
            orderDir = "desc";
        }

        List<TbItem> tbItemList = tbItemMapper.selectItemFront(cid, orderCol, orderDir, priceGt, priceLte);
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItemList);

        for (TbItem tbItem : tbItemList) {
            Product product = DtoUtil.TbItem2Product(tbItem);
            list.add(product);
        }

        allGoodsResult.setData(list);
        allGoodsResult.setTotal((int) pageInfo.getTotal());

        return allGoodsResult;
    }


    public String getIndexRedis() {

        try {
            String json = jedisUtil.get(RedisKeyEnum.PRODUCT_HOME.getKey());
            return json;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return "";
    }


    public int updateIndexRedis() {

        deleteHomeRedis();
        return 1;
    }


    public String getRecommendRedis() {

        try {
            String json = jedisUtil.get(RedisKeyEnum.RECOMEED_PANEL.getKey());
            return json;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return "";
    }


    public int updateRecommendRedis() {

        try {
            jedisUtil.del(RedisKeyEnum.RECOMEED_PANEL.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }


    public String getThankRedis() {

        try {
            String json = jedisUtil.get(RedisKeyEnum.THANK_PANEL.getKey());
            return json;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return "";
    }


    public int updateThankRedis() {

        try {
            jedisUtil.del(RedisKeyEnum.THANK_PANEL.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void updateNavListRedis() {

        try {
            jedisUtil.del(RedisKeyEnum.HEADER_PANEL.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<TbPanelContent> getNavList() {

        List<TbPanelContent> list = new ArrayList<>();
        //查询缓存
        try {
            //有缓存则读取
            String json = jedisUtil.get(RedisKeyEnum.HEADER_PANEL.getKey());
            if (json != null) {
                list = JSON.parseObject(json, new TypeToken<List<TbPanelContent>>() {}.getType());
                log.info("读取了导航栏缓存");
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbPanelContentExample exampleContent = new TbPanelContentExample();
        exampleContent.setOrderByClause("sort_order");
        TbPanelContentExample.Criteria criteriaContent = exampleContent.createCriteria();
        //条件查询
        criteriaContent.andPanelIdEqualTo(HEADER_PANEL_ID);
        list = tbPanelContentMapper.selectByExample(exampleContent);

        //把结果添加至缓存
        try {
            jedisUtil.set(RedisKeyEnum.HEADER_PANEL.getKey(), JSON.toJSONString(list));
            log.info("添加了导航栏缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 同步首页缓存
     */
    public void deleteHomeRedis() {
        try {
            jedisUtil.del(RedisKeyEnum.PRODUCT_HOME.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
