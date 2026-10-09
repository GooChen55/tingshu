package com.atguigu.tingshu.album.mapper;

import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.query.album.AlbumInfoQuery;
import com.atguigu.tingshu.vo.album.AlbumListVo;
import com.atguigu.tingshu.vo.album.AlbumStatVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlbumInfoMapper extends BaseMapper<AlbumInfo> {

    /**
     * 查看当前用户专辑分页列表（包含统计信息）
     *
     * @param pageInfo MP分页对象,动态SQL实现分页 第一个参数必须IPage
     * @param query    查询条件
     * @return MP分页对象
     */
    IPage<AlbumListVo> findUserAlbumPage(IPage<AlbumListVo> pageInfo, @Param("vo") AlbumInfoQuery query);

    /**
     * 根据专辑ID查询统计信息
     * @param albumId
     * @return
     */
    AlbumStatVo getAlbumStatVo(@Param("albumId") Long albumId);
}
