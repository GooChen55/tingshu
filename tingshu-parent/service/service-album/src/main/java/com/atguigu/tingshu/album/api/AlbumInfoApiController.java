package com.atguigu.tingshu.album.api;

import com.atguigu.tingshu.album.service.AlbumInfoService;
import com.atguigu.tingshu.common.login.GuiGuLogin;
import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.common.util.AuthContextHolder;
import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.query.album.AlbumInfoQuery;
import com.atguigu.tingshu.vo.album.AlbumInfoVo;
import com.atguigu.tingshu.vo.album.AlbumListVo;
import com.atguigu.tingshu.vo.album.AlbumStatVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "专辑管理")
@RestController
@RequestMapping("api/album")
@SuppressWarnings({"all"})
public class AlbumInfoApiController {

    @Autowired
    private AlbumInfoService albumInfoService;


    /**
     * 该接口必须登录才能访问
     * 保存专辑信息
     *
     * @param albumInfoVo
     * @return
     */
    @Operation(summary = "保存专辑信息")
    @PostMapping("/albumInfo/saveAlbumInfo")
    @GuiGuLogin
    public Result saveAlbumInfo(@RequestBody @Validated AlbumInfoVo albumInfoVo) {
        //1.获取当前用户ID 目前获取到是硬编码为1的用户ID
        Long userId = AuthContextHolder.getUserId();
        //2.调用业务逻辑
        albumInfoService.saveAlbumInfo(albumInfoVo, userId);
        //3.返回结果
        return Result.ok();
    }

    /***
     *  该接口必须登录才能访问
     * 查看当前用户专辑分页列表（包含统计信息）
     * @param page 页码
     * @param limit 页大小
     * @param query 查询条件
     * @return MP分页对象
     */
    @Operation(summary = "查看当前用户专辑分页列表（包含统计信息）")
    @PostMapping("/albumInfo/findUserAlbumPage/{page}/{limit}")
    @GuiGuLogin(required = true) // 登录拦截，如果未登录不允许访问，反之登录可以执行调用
    public Result<IPage<AlbumListVo>> findUserAlbumPage(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody AlbumInfoQuery query
    ){
        //1.获取当前用户ID
        Long userId = AuthContextHolder.getUserId();
        //2.创建分页对象，封装页码、页大小
        IPage<AlbumListVo> pageInfo = new Page<>(page, limit);
        //3.调用业务逻辑，最终执行持久层查询 封装分页集合、总记录数、总页数
        query.setUserId(userId);
        pageInfo = albumInfoService.findUserAlbumPage(pageInfo, query);
        //4.返回分页结果
        return Result.ok(pageInfo);
    }


    /**
     * 删除专辑
     * @param id
     * @return
     */
    @Operation(summary = "删除专辑")
    @DeleteMapping("/albumInfo/removeAlbumInfo/{id}")
    public Result removeAlbumInfo(@PathVariable Long id){
        albumInfoService.removeAlbumInfo(id);
        return Result.ok();
    }


    /**
     * 根据专辑ID查询专辑信息（包含标签列表）
     * @param id 专辑ID
     * @return
     */
    @Operation(summary = "根据专辑ID查询专辑信息")
    @GetMapping("/albumInfo/getAlbumInfo/{id}")
    public Result<AlbumInfo> getAlbumInfo(@PathVariable Long id){
        AlbumInfo albumInfo = albumInfoService.getAlbumInfo(id);
        return Result.ok(albumInfo);
    }

    /**
     * 更新专辑信息
     * @param id
     * @param albumInfoVo
     * @return
     */
    @Operation(summary = "更新专辑信息")
    @PutMapping("/albumInfo/updateAlbumInfo/{id}")
    public Result updateAlbumInfo(@PathVariable Long id, @RequestBody @Validated AlbumInfoVo albumInfoVo){
        albumInfoService.updateAlbumInfo(id, albumInfoVo);
        return Result.ok();
    }


    /**
     *  该接口必须登录才能访问
     * @return
     */
    @GuiGuLogin
    @Operation(summary = "查询当前用户专辑列表")
    @GetMapping("/albumInfo/findUserAllAlbumList")
    public Result<List<AlbumInfo>> findUserAllAlbumList(){
        //1.获取当前用户ID
        Long userId = AuthContextHolder.getUserId();
        //2.调用业务逻辑
        List<AlbumInfo> list = albumInfoService.findUserAllAlbumList(userId);
        //3.返回结果
        return Result.ok(list);
    }

    /**
     * 根据专辑ID查询统计信息
     * @param albumId
     * @return
     */
    @Operation(summary = "根据专辑ID查询统计信息")
    @GetMapping("/albumInfo/getAlbumStatVo/{albumId}")
    public Result<AlbumStatVo> getAlbumStatVo(@PathVariable Long albumId){
        AlbumStatVo albumStatVo = albumInfoService.getAlbumStatVo(albumId);
        return Result.ok(albumStatVo);
    }

}

