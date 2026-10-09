package com.atguigu.tingshu.search.service;

import java.util.Map;

public interface ItemService {

    /**
     * 查询专辑详情-汇总详情页渲染所需参数
     * @param albumId
     * @return {announcer:主播信息,albumInfo:专辑对象,albumStatVo:统计对象, baseCategoryView:分类对象}
     */
    Map<String, Object> item(Long albumId);
}
