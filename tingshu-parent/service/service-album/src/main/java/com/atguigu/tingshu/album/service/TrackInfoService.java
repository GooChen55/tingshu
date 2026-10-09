package com.atguigu.tingshu.album.service;

import com.atguigu.tingshu.model.album.TrackInfo;
import com.atguigu.tingshu.query.album.TrackInfoQuery;
import com.atguigu.tingshu.vo.album.AlbumTrackListVo;
import com.atguigu.tingshu.vo.album.TrackInfoVo;
import com.atguigu.tingshu.vo.album.TrackListVo;
import com.atguigu.tingshu.vo.album.TrackStatMqVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TrackInfoService extends IService<TrackInfo> {

    /**
     * 保存声音信息
     * @param userId
     * @param trackInfoVo
     */
    void saveTrackInfo(Long userId, TrackInfoVo trackInfoVo);

    /**
     * 保存声音统计信息
     * @param trackId 声音ID
     * @param statType 统计类型
     * @param statNum 统计数值
     */
    void saveTrackStat(Long trackId, String statType, int statNum);

    /**
     * 查询分页查询声音列表（包含统计信息）
     * @param pageInfo MP分页对象
     * @param trackInfoQuery 查询条件
     * @return MP分页对象
     */
    IPage<TrackListVo> findUserTrackPage(IPage<TrackListVo> pageInfo, TrackInfoQuery trackInfoQuery);

    /**
     * 更新声音信息
     * @param id 声音ID
     * @param trackInfoVo 声音VO信息
     */
    void updateTrackInfo(Long id, TrackInfoVo trackInfoVo);

    /**
     * 删除声音信息（包括音频文件）
     * @param id 声音ID
     * @return
     */
    void removeTrackInfo(Long id);

    /**
     * 根据专辑ID分页查询声音列表包含统计信息（动态渲染付费标识）
     * @param albumId 专辑ID
     * @param userId 用户ID
     * @return 分页对象
     */
    IPage<AlbumTrackListVo> findAlbumTrackPage(IPage<AlbumTrackListVo> pageInfo, Long albumId, Long userId);

    /**
     * 增量更新声音统计数值
     * @param trackStatMqVo
     */
    void updateTrackStat(TrackStatMqVo trackStatMqVo);
}
