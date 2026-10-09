package com.atguigu.tingshu.album.mapper;

import com.atguigu.tingshu.model.album.BaseAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseAttributeMapper extends BaseMapper<BaseAttribute> {


    /**
     * 根据1级分类ID查询标签列表（包含标签取值）根据1级分类ID查询标签列表（包含标签取值）
     * @param category1Id 1级分类ID
     * @return
     */
    List<BaseAttribute> findAttributeByCategory1Id(@Param("category1Id") Long category1Id);
}
