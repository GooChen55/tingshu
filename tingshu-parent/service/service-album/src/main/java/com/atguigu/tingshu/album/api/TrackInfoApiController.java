package com.atguigu.tingshu.album.api;

import com.atguigu.tingshu.album.service.TrackInfoService;
import com.atguigu.tingshu.album.service.VodService;
import com.atguigu.tingshu.common.login.GuiGuLogin;
import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.common.util.AuthContextHolder;
import com.atguigu.tingshu.model.album.TrackInfo;
import com.atguigu.tingshu.query.album.TrackInfoQuery;
import com.atguigu.tingshu.vo.album.AlbumTrackListVo;
import com.atguigu.tingshu.vo.album.TrackInfoVo;
import com.atguigu.tingshu.vo.album.TrackListVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name = "声音管理")
@RestController
@RequestMapping("api/album")
@SuppressWarnings({"all"})
public class    TrackInfoApiController {

    @Autowired
    private TrackInfoService trackInfoService;

    @Autowired
    private VodService vodService;


    /**
     * 文件上传，将音视频文件上传到点播平台
     *
     * @param file 文件
     * @return {mediaFileId:"文件唯一标识",mediaUrl:"播放地址"}
     */
    @Operation(summary = "将音视频文件上传到点播平台")
    @PostMapping("/trackInfo/uploadTrack")
    public Result<Map<String, String>> uploadTrack(@RequestParam("file") MultipartFile file) {
        Map<String, String> map = vodService.uploadTrack(file);
        return Result.ok(map);
    }

    /**
     * 该接口必须登录后才能访问
     * 保存声音信息
     *
     * @param trackInfoVo
     * @return
     */
    @GuiGuLogin
    @PostMapping("/trackInfo/saveTrackInfo")
    @Operation(summary = "保存声音信息")
    public Result saveTrackInfo(@RequestBody @Validated TrackInfoVo trackInfoVo) {
        //1. 获取当用户ID
        Long userId = AuthContextHolder.getUserId();
        //2. 调用业务逻辑
        trackInfoService.saveTrackInfo(userId, trackInfoVo);
        //3. 返回结果
        return Result.ok();
    }

    /**
     * 该接口必须登录后才能访问
     * 分页查询当前用户声音列表（包含统计信息）
     *
     * @param page           页码
     * @param limit          页大小
     * @param trackInfoQuery 查询条件
     * @return MP分页对象
     */
    @GuiGuLogin
    @Operation(summary = "分页查询当前用户声音列表（包含统计信息）")
    @PostMapping("/trackInfo/findUserTrackPage/{page}/{limit}")
    public Result<IPage<TrackListVo>> findUserTrackPage(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody TrackInfoQuery trackInfoQuery
    ) {
        //1. 获取当前用户ID
        Long userId = AuthContextHolder.getUserId();
        trackInfoQuery.setUserId(userId);
        //2. 创建分页对象 封装页码、页大小
        IPage<TrackListVo> pageInfo = new Page<>(page, limit);
        //3. 调用业务逻辑
        pageInfo = trackInfoService.findUserTrackPage(pageInfo, trackInfoQuery);
        //4. 返回结果
        return Result.ok(pageInfo);
    }


    /**
     * 根据声音ID查询声音信息
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据声音ID查询声音信息")
    @GetMapping("/trackInfo/getTrackInfo/{id}")
    public Result<TrackInfo> getTrackInfo(@PathVariable Long id) {
        TrackInfo trackInfo = trackInfoService.getById(id);
        return Result.ok(trackInfo);
    }



    @Operation(summary = "更新声音信息")
    @PutMapping("/trackInfo/updateTrackInfo/{id}")
    public Result updateTrackInfo(@PathVariable Long id, @RequestBody @Validated TrackInfoVo trackInfoVo) {
        trackInfoService.updateTrackInfo(id, trackInfoVo);
        return Result.ok();
    }


    /**
     * 删除声音信息（包括音频文件）
     * @param id 声音ID
     * @return
     */
    @Operation(summary = "删除声音信息（包括音频文件）")
    @DeleteMapping("/trackInfo/removeTrackInfo/{id}")
    public Result removeTrackInfo(@PathVariable Long id){
        trackInfoService.removeTrackInfo(id);
        return Result.ok();
    }

    /**
     * 根据专辑ID分页查询声音列表包含统计信息（动态渲染付费标识）
     * @param albumId 专辑ID
     * @param page 页码
     * @param limit 页大小
     * @return 分页对象
     */
    @GuiGuLogin(required = false)
    @Operation(summary = "根据专辑ID分页查询声音列表包含统计信息（动态渲染付费标识）")
    @GetMapping("/trackInfo/findAlbumTrackPage/{albumId}/{page}/{limit}")
    public Result<IPage<AlbumTrackListVo>> findAlbumTrackPage(
            @PathVariable Long albumId,
            @PathVariable Long page,
            @PathVariable Long limit
    ){
        //1.获取当前用户ID（可能为空）
        Long userId = AuthContextHolder.getUserId();
        //2.创建分页对象 封装页码、页大小
        IPage<AlbumTrackListVo> pageInfo = new Page<>(page, limit);
        //3.调用业务逻辑
        pageInfo = trackInfoService.findAlbumTrackPage(pageInfo, albumId, userId);
        //4.返回分页结果
        return Result.ok(pageInfo);
    }
}

