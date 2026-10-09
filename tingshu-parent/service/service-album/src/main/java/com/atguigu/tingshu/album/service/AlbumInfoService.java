package com.atguigu.tingshu.album.service;

import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.query.album.AlbumInfoQuery;
import com.atguigu.tingshu.vo.album.AlbumInfoVo;
import com.atguigu.tingshu.vo.album.AlbumListVo;
import com.atguigu.tingshu.vo.album.AlbumStatVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AlbumInfoService extends IService<AlbumInfo> {


    /***
     * 保存专辑信息
     * @param albumInfoVo 专辑VO信息
     * @param userId 用户ID
     */
    void saveAlbumInfo(AlbumInfoVo albumInfoVo, Long userId);

    /**
     * 保存专辑统计信息
     * @param albumId 专辑ID
     * @param statType 统计类型
     * @param statNum 统计数值 0401-播放量 0402-订阅量 0403-购买量 0403-评论数'
     */
    void saveAlbumInfoStat(Long albumId, String statType, int statNum);

    /**
     * 查看当前用户专辑分页列表（包含统计信息）
     * @param pageInfo MP分页对象
     * @param query 查询条件
     * @return MP分页对象
     */
    IPage<AlbumListVo> findUserAlbumPage(IPage<AlbumListVo> pageInfo, AlbumInfoQuery query);

    /**
     * 删除专辑
     * @param id 专辑ID
     */
    void removeAlbumInfo(Long id);

    /**
     * 根据专辑ID查询专辑信息（包含标签列表）
     * @param id 专辑ID
     * @return
     */
    AlbumInfo getAlbumInfo(Long id);

    /**
     * 更新专辑信息
     * @param id 专辑ID
     * @param albumInfoVo 专辑VO
     * @return
     */
    void updateAlbumInfo(Long id, AlbumInfoVo albumInfoVo);

    /**
     * 查询指定用户专辑列表
     * @param userId
     * @return
     */
    List<AlbumInfo> findUserAllAlbumList(Long userId);

    /**
     * 根据专辑ID查询统计信息
     * @param albumId
     * @return
     */
    AlbumStatVo getAlbumStatVo(Long albumId);
}
