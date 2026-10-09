package com.atguigu.tingshu.album.api;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.tingshu.album.service.BaseCategoryService;
import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.model.album.BaseAttribute;
import com.atguigu.tingshu.model.album.BaseCategory3;
import com.atguigu.tingshu.model.album.BaseCategoryView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@Tag(name = "分类管理")
@RestController
@RequestMapping(value = "/api/album")
@SuppressWarnings({"all"})
public class BaseCategoryApiController {

    @Autowired
    private BaseCategoryService baseCategoryService;


    /**
     * 查询所有1级分类（包含2级分类以及3级分类列表）
     *
     * @return [{categoryId:1,categoryName:"音乐",categoryChild:[{categoryId:101,categoryName:"音乐音效",categoryChild:[{categoryId:1001,categoryName:"催眠音乐"}]},{}]},{其他1级分类对象},{}]
     */
    @Operation(summary = "查询所有1级分类（包含2级分类以及3级分类列表）")
    @GetMapping("/category/getBaseCategoryList")
    public Result<List<JSONObject>> getBaseCategoryList() {
        List<JSONObject> list = baseCategoryService.getBaseCategoryList();
        return Result.ok(list);
    }


    /**
     * 根据1级分类ID查询标签列表（包含标签取值）根据1级分类ID查询标签列表（包含标签取值）
     *
     * @param category1Id
     * @return
     */
    @Operation(summary = "根据1级分类ID查询标签列表（包含标签取值）")
    @GetMapping("/category/findAttribute/{category1Id}")
    public Result<List<BaseAttribute>> findAttributeByCategory1Id(@PathVariable Long category1Id) {
        List<BaseAttribute> list = baseCategoryService.findAttributeByCategory1Id(category1Id);
        return Result.ok(list);
    }


    /**
     * 根据3级分类ID查询分类视图
     *
     * @param category3Id
     * @return
     */
    @Operation(summary = "根据3级分类ID查询分类视图")
    @GetMapping("/category/getCategoryView/{category3Id}")
    public Result<BaseCategoryView> getCategoryView(@PathVariable Long category3Id) {
        BaseCategoryView baseCategoryView = baseCategoryService.getCategoryView(category3Id);
        return Result.ok(baseCategoryView);
    }


    /**
     * 根据1级分类ID查询置顶7个三级分类列表
     *
     * @return
     */
    @Operation(summary = "根据1级分类ID查询置顶7个三级分类列表")
    @GetMapping("/category/findTopBaseCategory3/{category1Id}")
    public Result<List<BaseCategory3>> findTop7BaseCategory3(@PathVariable Long category1Id) {
        List<BaseCategory3> list = baseCategoryService.findTop7BaseCategory3(category1Id);
        return Result.ok(list);
    }


    /**
     * 查询1级分类对象（包含2级分类及2级分类包含3级分类）
     *
     * @param category1Id
     * @return
     */
    @Operation(summary = "查询1级分类对象（包含2级分类及2级分类包含3级分类）")
    @GetMapping("/category/getBaseCategoryList/{category1Id}")
    public Result<JSONObject> getBaseCategoryByCategory1Id(@PathVariable Long category1Id) {
        JSONObject jsonObject = baseCategoryService.getBaseCategoryByCategory1Id(category1Id);
        return Result.ok(jsonObject);
    }

}

