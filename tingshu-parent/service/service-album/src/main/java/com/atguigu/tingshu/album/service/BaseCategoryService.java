package com.atguigu.tingshu.album.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.tingshu.album.mapper.BaseCategory2Mapper;
import com.atguigu.tingshu.album.mapper.BaseCategory3Mapper;
import com.atguigu.tingshu.model.album.BaseAttribute;
import com.atguigu.tingshu.model.album.BaseCategory1;
import com.atguigu.tingshu.model.album.BaseCategory3;
import com.atguigu.tingshu.model.album.BaseCategoryView;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface BaseCategoryService extends IService<BaseCategory1> {

    /**
     * 查询所有1级分类（包含2级分类以及3级分类列表）
     * @return
     */
    List<JSONObject> getBaseCategoryList();

    /**
     * 根据1级分类ID查询标签列表（包含标签取值）根据1级分类ID查询标签列表（包含标签取值）
     * @param category1Id
     * @return
     */
    List<BaseAttribute> findAttributeByCategory1Id(Long category1Id);

    /**
     * 根据3级分类ID查询分类视图
     * @param category3Id
     * @return
     */
    BaseCategoryView getCategoryView(Long category3Id);

    /**
     * 根据1级分类ID查询置顶7个三级分类列表
     * @return
     */
    List<BaseCategory3> findTop7BaseCategory3(Long category1Id);

    /**
     * 查询1级分类对象（包含2级分类及2级分类包含3级分类）
     *
     * @param category1Id
     * @return
     */
    JSONObject getBaseCategoryByCategory1Id(Long category1Id);
}
