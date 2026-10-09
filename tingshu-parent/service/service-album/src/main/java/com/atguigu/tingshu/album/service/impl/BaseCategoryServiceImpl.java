package com.atguigu.tingshu.album.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.tingshu.album.mapper.*;
import com.atguigu.tingshu.album.service.BaseCategoryService;
import com.atguigu.tingshu.model.album.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"all"})
public class BaseCategoryServiceImpl extends ServiceImpl<BaseCategory1Mapper, BaseCategory1> implements BaseCategoryService {

    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;

    @Autowired
    private BaseAttributeMapper baseAttributeMapper;


    /**
     * 查询所有1级分类（包含2级分类以及3级分类列表）
     *
     * @return
     */
    @Override
    public List<JSONObject> getBaseCategoryList() {
        //1. 创建集合用于存放所有"1级"分类JSON对象
        List<JSONObject> list = new ArrayList<>();
        //2. 处理1级分类
        //2.1 查询分类视图得到共计401条分类视图对象
        List<BaseCategoryView> baseCategoryViewList = baseCategoryViewMapper.selectList(null);
        //2.2 按1级分类ID进行分组 得到 "1级"分类Map<1级分类ID, 1级分类列表>
        Map<Long, List<BaseCategoryView>> map1 = baseCategoryViewList.stream()
                .collect(Collectors.groupingBy(BaseCategoryView::getCategory1Id));

        //2.3 遍历"1级"分类Map 封装1级分类JSON对象
        for (Map.Entry<Long, List<BaseCategoryView>> entry1 : map1.entrySet()) {
            //2.3.1 创建1级分类JSON对象
            JSONObject jsonObject1 = new JSONObject();
            //2.3.2 封装1级分类对象中分类ID、分类名称
            jsonObject1.put("categoryId", entry1.getKey());
            jsonObject1.put("categoryName", entry1.getValue().get(0).getCategory1Name());
            //3.  在当前1级级分类中，处理2级分类
            //3.1 创建集合用于2级分类对象
            ArrayList<JSONObject> jsonObject2List = new ArrayList<>();
            //3.2 对"1级"分类列表按2级分类ID进行分组 得到"2级"分类Map<2级分类ID, "2级分类"列表>
            Map<Long, List<BaseCategoryView>> map2 = entry1.getValue()
                    .stream()
                    .collect(Collectors.groupingBy(BaseCategoryView::getCategory2Id));
            //3.3 遍历"2级"分类Map 封装2级分类JSON对象
            for (Map.Entry<Long, List<BaseCategoryView>> entry2 : map2.entrySet()) {
                //3.3.1 封装2级分类JSON对象
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("categoryId", entry2.getKey());
                jsonObject2.put("categoryName", entry2.getValue().get(0).getCategory2Name());
                //3.3.2 将2级分类对象存入2级分类集合
                jsonObject2List.add(jsonObject2);
                //4.  在当前2级级分类中，处理3级分类
                //4.1 创建集合用于3级分类对象
                ArrayList<JSONObject> jsonObject3List = new ArrayList<>();
                //4.2 遍历"2级"分类列表 封装2级分类JSON对象
                for (BaseCategoryView baseCategoryView : entry2.getValue()) {
                    //4.3 封装3级分类JSON对象
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("categoryId", baseCategoryView.getCategory3Id());
                    jsonObject3.put("categoryName", baseCategoryView.getCategory3Name());
                    //4.4 将3级分类对象存入3级分类集合
                    jsonObject3List.add(jsonObject3);
                }
                //4.5 将3级分类集合存入2级分类对象"categoryChild"属性中
                jsonObject2.put("categoryChild", jsonObject3List);
            }
            //3.4 将2级分类集合存入1级分类对象"categoryChild"属性中
            jsonObject1.put("categoryChild", jsonObject2List);

            //2.3.2 将1级分类JSON对象加入到集合中
            list.add(jsonObject1);
        }
        //5.返回1级分类集合
        return list;
    }


    /**
     * 根据1级分类ID查询标签列表（包含标签取值）根据1级分类ID查询标签列表（包含标签取值）
     *
     * @param category1Id
     * @return
     */
    @Override
    public List<BaseAttribute> findAttributeByCategory1Id(Long category1Id) {
        //调用标签持久层执行自定义SQL查询
        return baseAttributeMapper.findAttributeByCategory1Id(category1Id);
    }

    /**
     * 根据3级分类ID查询分类视图
     *
     * @param category3Id
     * @return
     */
    @Override
    public BaseCategoryView getCategoryView(Long category3Id) {
        return baseCategoryViewMapper.selectById(category3Id);
    }

    /**
     * 根据1级分类ID查询置顶7个三级分类列表
     *
     * @return
     */
    @Override
    public List<BaseCategory3> findTop7BaseCategory3(Long category1Id) {
        //1.根据1级分类ID得到2级分类ID列表
        List<BaseCategory2> baseCategory2List = baseCategory2Mapper.selectList(
                new LambdaQueryWrapper<BaseCategory2>()
                        .eq(BaseCategory2::getCategory1Id, category1Id)
                        .select(BaseCategory2::getId)
        );

        //2.根据2级分类ID列表得到3级置顶分类列表
        if (CollUtil.isNotEmpty(baseCategory2List)) {
            List<Long> category2IdList = baseCategory2List.stream()
                    .map(BaseCategory2::getId).collect(Collectors.toList());
            List<BaseCategory3> baseCategory3List = baseCategory3Mapper.selectList(
                    new LambdaQueryWrapper<BaseCategory3>()
                            .eq(BaseCategory3::getIsTop, 1)
                            .in(BaseCategory3::getCategory2Id, category2IdList)
                            .orderByAsc(BaseCategory3::getOrderNum)
                            .select(BaseCategory3::getId, BaseCategory3::getName, BaseCategory3::getCategory2Id)
                            .last("limit 7")
            );
            return baseCategory3List;
        }
        return List.of();
    }

    /**
     * 查询1级分类对象（包含2级分类及2级分类包含3级分类）
     *
     * @param category1Id
     * @return
     */
    @Override
    public JSONObject getBaseCategoryByCategory1Id(Long category1Id) {
        //1.根据1级分类ID查询分类视图得到"1级"分类列表
        List<BaseCategoryView> baseCategory1List = baseCategoryViewMapper.selectList(
                new LambdaQueryWrapper<BaseCategoryView>().eq(BaseCategoryView::getCategory1Id, category1Id)
        );
        //2.创建1级分类JSON对象 封装1级分类对象中分类ID、分类名称
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("categoryId", baseCategory1List.get(0).getCategory1Id());
        jsonObject1.put("categoryName", baseCategory1List.get(0).getCategory1Name());

        //3.处理2级分类
        //3.1 根据2级分类ID进行分组 得到"2级分类"Map
        Map<Long, List<BaseCategoryView>> map2 = baseCategory1List.stream()
                .collect(Collectors.groupingBy(BaseCategoryView::getCategory2Id));
        //3.2 遍历"2级"分类Map 封装2级分类JSON对象
        ArrayList<JSONObject> jsonObject2List = new ArrayList<>();
        for (Map.Entry<Long, List<BaseCategoryView>> entry2 : map2.entrySet()) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("categoryId", entry2.getKey());
            jsonObject2.put("categoryName", entry2.getValue().get(0).getCategory2Name());
            //3.3 将2级分类对象存入2级分类集合
            jsonObject2List.add(jsonObject2);
            //4.处理3级分类
            ArrayList<JSONObject> jsonObject3List = new ArrayList<>();
            //4.2 遍历"2级"分类列表 封装3级分类JSON对象
            for (BaseCategoryView baseCategoryView : entry2.getValue()) {
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("categoryId", baseCategoryView.getCategory3Id());
                jsonObject3.put("categoryName", baseCategoryView.getCategory3Name());
                //4.3 将3级分类对象存入3级分类集合
                jsonObject3List.add(jsonObject3);
            }
            //4.4 将3级分类集合存入2级分类对象"categoryChild"属性中
            jsonObject2.put("categoryChild", jsonObject3List);
        }
        //3.4 将2级分类集合存入1级分类对象"categoryChild"属性中
        jsonObject1.put("categoryChild", jsonObject2List);
        //4.返回1级分类JSON对象
        return jsonObject1;
    }
}
