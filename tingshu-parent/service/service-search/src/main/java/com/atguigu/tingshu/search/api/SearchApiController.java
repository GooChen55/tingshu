package com.atguigu.tingshu.search.api;

import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.query.search.AlbumIndexQuery;
import com.atguigu.tingshu.search.service.SearchService;
import com.atguigu.tingshu.vo.search.AlbumSearchResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "搜索专辑管理")
@RestController
@RequestMapping("api/search")
@SuppressWarnings({"all"})
public class SearchApiController {

    @Autowired
    private SearchService searchService;

    /**
     * 上架专辑-导入索引库
     * @param albumId 专辑ID
     * @return
     */
    @Operation(summary = "上架专辑-导入索引库")
    @GetMapping("/albumInfo/upperAlbum/{albumId}")
    public Result upperAlbum(@PathVariable Long albumId) {
        searchService.saveAlbumInfoIndex(albumId);
        return Result.ok();
    }

    /**
     * 下架专辑-删除索引库文档
     * @param albumId 专辑ID
     * @return
     */
    @Operation(summary = "下架专辑-删除索引库文档")
    @GetMapping("/albumInfo/lowerAlbum/{albumId}")
    public Result lowerAlbum(@PathVariable Long albumId) {
        searchService.removeAlbumInfoIndex(albumId);
        return Result.ok();
    }

    /**
     * 站内专辑检索（关键词、分类、属性筛选、排序、分页、高亮）
     * @param query 检索参数
     * @return 检索结果
     */
    @Operation(summary = "站内专辑检索")
    @PostMapping("/albumInfo")
    public Result<AlbumSearchResponseVo> search(@RequestBody AlbumIndexQuery query) {
        AlbumSearchResponseVo result = searchService.search(query);
        return Result.ok(result);
    }


    /**
     * 查询1级分类下置顶分类热门专辑
     * @param category1Id
     * @return
     */
    @Operation(summary = "查询1级分类下置顶分类热门专辑")
    @GetMapping("/albumInfo/channel/{category1Id}")
    public Result<List<Map<String, Object>>> channel(@PathVariable Long category1Id){
        List<Map<String, Object>> list = searchService.channel(category1Id);
        return Result.ok(list);
    }


    /**
     * 搜索关键词自动补全
     * @param keyword 用户已录入字符
     * @return ["待选项1","待选项2"]
     */
    @Operation(summary = "搜索关键词自动补全")
    @GetMapping("/albumInfo/completeSuggest/{keyword}")
    public Result<List<String>> completeSuggest(@PathVariable String keyword){
        List<String> list = searchService.completeSuggest(keyword);
        return Result.ok(list);
    }
}

