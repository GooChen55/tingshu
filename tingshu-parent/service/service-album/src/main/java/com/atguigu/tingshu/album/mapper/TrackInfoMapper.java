package com.atguigu.tingshu.album.mapper;

import com.atguigu.tingshu.model.album.TrackInfo;
import com.atguigu.tingshu.query.album.TrackInfoQuery;
import com.atguigu.tingshu.vo.album.AlbumTrackListVo;
import com.atguigu.tingshu.vo.album.TrackListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TrackInfoMapper extends BaseMapper<TrackInfo> {


    /**
     * 查询分页查询声音列表（包含统计信息）
     * @param pageInfo MP分页对象
     * @param trackInfoQuery 查询条件
     * @return MP分页对象
     */
    IPage<TrackListVo> findUserTrackPage(IPage<TrackListVo> pageInfo, @Param("vo") TrackInfoQuery trackInfoQuery);

    /**
     * 根据专辑ID分页查询声音列表包含统计信息（动态渲染付费标识）
     * @param pageInfo 分页对象
     * @param albumId 专辑ID
     * @return 分页对象
     */
    IPage<AlbumTrackListVo> findAlbumTrackPage(IPage<AlbumTrackListVo> pageInfo, @Param("albumId") Long albumId);
}
