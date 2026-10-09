package com.atguigu.tingshu.album.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.atguigu.tingshu.album.mapper.AlbumInfoMapper;
import com.atguigu.tingshu.album.mapper.AlbumStatMapper;
import com.atguigu.tingshu.album.mapper.TrackInfoMapper;
import com.atguigu.tingshu.album.mapper.TrackStatMapper;
import com.atguigu.tingshu.album.service.AuditService;
import com.atguigu.tingshu.album.service.TrackInfoService;
import com.atguigu.tingshu.album.service.VodService;
import com.atguigu.tingshu.common.constant.SystemConstant;
import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.model.album.AlbumStat;
import com.atguigu.tingshu.model.album.TrackInfo;
import com.atguigu.tingshu.model.album.TrackStat;
import com.atguigu.tingshu.query.album.TrackInfoQuery;
import com.atguigu.tingshu.user.client.UserFeignClient;
import com.atguigu.tingshu.vo.album.*;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.atguigu.tingshu.common.constant.SystemConstant.*;

@Slf4j
@Service
@SuppressWarnings({"all"})
public class TrackInfoServiceImpl extends ServiceImpl<TrackInfoMapper, TrackInfo> implements TrackInfoService {

    @Autowired
    private TrackInfoMapper trackInfoMapper;

    @Autowired
    private AlbumInfoMapper albumInfoMapper;

    @Autowired
    private VodService vodService;

    @Autowired
    private AuditService auditService;
    @Autowired
    private AlbumStatMapper albumStatMapper;

    /**
     * 保存声音信息
     *
     * @param userId
     * @param trackInfoVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTrackInfo(Long userId, TrackInfoVo trackInfoVo) {
        //1.根据所属专辑ID查询专辑信息，得到现有声音数量
        AlbumInfo albumInfo = albumInfoMapper.selectById(trackInfoVo.getAlbumId());
        Integer includeTrackCount = albumInfo.getIncludeTrackCount();
        //2.保存声音信息
        TrackInfo trackInfo = BeanUtil.copyProperties(trackInfoVo, TrackInfo.class);
        //2.1 封装声音属性 用户ID、序号、封面图片、审核状态
        trackInfo.setUserId(userId);
        trackInfo.setOrderNum(includeTrackCount + 1);
        trackInfo.setSource(SystemConstant.TRACK_SOURCE_USER);
        if (StringUtils.isBlank(trackInfoVo.getCoverUrl())) {
            trackInfo.setCoverUrl(albumInfo.getCoverUrl());
        }
        trackInfo.setStatus(SystemConstant.TRACK_STATUS_NO_PASS);
        //2.2 调用云点播平台获取音频：时长、大小、类型
        TrackMediaInfoVo trackMediaInfoVo = vodService.getMediaInfo(trackInfo.getMediaFileId());
        if (trackMediaInfoVo != null) {
            trackInfo.setMediaDuration(BigDecimal.valueOf(trackMediaInfoVo.getDuration()));
            trackInfo.setMediaSize(trackMediaInfoVo.getSize());
            trackInfo.setMediaType(trackMediaInfoVo.getType());
        }
        //2.3 保存声音信息,得到声音ID
        trackInfoMapper.insert(trackInfo);
        Long trackId = trackInfo.getId();

        //3.更新专辑信息：声音数量
        AlbumInfo albumInfo_update = new AlbumInfo();
        albumInfo_update.setIncludeTrackCount(includeTrackCount + 1);
        albumInfo_update.setId(albumInfo.getId());
        albumInfoMapper.updateById(albumInfo_update);

        //4.新增声音统计信息：播放 点赞 收藏 评论
        this.saveTrackStat(trackId, SystemConstant.TRACK_STAT_PLAY, 0);
        this.saveTrackStat(trackId, SystemConstant.TRACK_STAT_COLLECT, 0);
        this.saveTrackStat(trackId, SystemConstant.TRACK_STAT_PRAISE, 0);
        this.saveTrackStat(trackId, SystemConstant.TRACK_STAT_COMMENT, 0);

        //5.内容进行审核：文本、音频
        String text = trackInfo.getTrackTitle() + trackInfo.getTrackIntro();
        String suggestion = auditService.audit_text(text);
        if (StringUtils.isNotBlank(suggestion)) {
            if ("block".equals(suggestion)) {
                trackInfo.setStatus(TRACK_STATUS_NO_PASS);
            } else if ("review".equals(suggestion)) {
                trackInfo.setStatus(TRACK_STATUS_MANUAL);
            } else if ("pass".equals(suggestion)) {
                trackInfo.setStatus(TRACK_STATUS_PASS);
                //TODO 如果文本审核通过进一步对音频进行审核:此处发起离线审核任务 将声音状态：审核中
                String taskId = auditService.startReviewTask(trackInfo.getMediaFileId());
                trackInfo.setStatus(TRACK_STATUS_REVIEWING);
                // 将审核任务ID关联到声音表
                trackInfo.setReviewTaskId(taskId);
            }
            trackInfoMapper.updateById(trackInfo);
        }
    }

    @Autowired
    private TrackStatMapper trackStatMapper;

    /**
     * 保存声音统计信息
     *
     * @param trackId  声音ID
     * @param statType 统计类型
     * @param statNum  统计数值
     */
    @Override
    public void saveTrackStat(Long trackId, String statType, int statNum) {
        TrackStat trackStat = new TrackStat();
        trackStat.setTrackId(trackId);
        trackStat.setStatType(statType);
        trackStat.setStatNum(statNum);
        trackStatMapper.insert(trackStat);
    }

    /**
     * 查询分页查询声音列表（包含统计信息）
     *
     * @param pageInfo       MP分页对象
     * @param trackInfoQuery 查询条件
     * @return MP分页对象
     */
    @Override
    public IPage<TrackListVo> findUserTrackPage(IPage<TrackListVo> pageInfo, TrackInfoQuery trackInfoQuery) {
        return trackInfoMapper.findUserTrackPage(pageInfo, trackInfoQuery);
    }

    /**
     * 更新声音信息
     *
     * @param id          声音ID
     * @param trackInfoVo 声音VO信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTrackInfo(Long id, TrackInfoVo trackInfoVo) {
        //1.根据声音ID查询声音记录 得到 原来音频唯一标识
        TrackInfo trackInfo = trackInfoMapper.selectById(id);
        String oldMediaFileId = trackInfo.getMediaFileId();

        //将vo修改信息拷贝到trackInfo中
        BeanUtil.copyProperties(trackInfoVo, trackInfo);

        //TODO 内容进行审核：文本、音频
        String text = trackInfo.getTrackTitle() + trackInfo.getTrackIntro();
        String suggestion = auditService.audit_text(text);
        if (StringUtils.isNotBlank(suggestion)) {
            if ("block".equals(suggestion)) {
                trackInfo.setStatus(TRACK_STATUS_NO_PASS);
            } else if ("review".equals(suggestion)) {
                trackInfo.setStatus(TRACK_STATUS_MANUAL);
            } else if ("pass".equals(suggestion)) {
                trackInfo.setStatus(TRACK_STATUS_PASS);
            }
        }

        //2.判断音频是否修改
        if (!oldMediaFileId.equals(trackInfoVo.getMediaFileId())) {
            //2.1 说明音频文件更新了
            TrackMediaInfoVo mediaInfo = vodService.getMediaInfo(trackInfoVo.getMediaFileId());
            if (mediaInfo != null) {
                trackInfo.setMediaFileId(trackInfoVo.getMediaFileId());
                trackInfo.setMediaUrl(trackInfoVo.getMediaFileId());
                trackInfo.setMediaType(mediaInfo.getType());
                trackInfo.setMediaDuration(BigDecimal.valueOf(mediaInfo.getDuration()));
                trackInfo.setMediaSize(mediaInfo.getSize());

                //TODO 如果文本审核通过进一步对音频进行审核:此处发起离线审核任务 将声音状态：审核中
                String taskId = auditService.startReviewTask(trackInfo.getMediaFileId());
                trackInfo.setStatus(TRACK_STATUS_REVIEWING);
                // 将审核任务ID关联到声音表
                trackInfo.setReviewTaskId(taskId);
            }
            //2.2 删除原来的音频文件
            vodService.deleteMedia(oldMediaFileId);
            //2.3 TODO 如果音频文件更新了，对新音频再次进行审核
        }
        //3.更新声音信息
        trackInfoMapper.updateById(trackInfo);

    }

    /**
     * 删除声音信息（包括音频文件）
     *
     * @param id 声音ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeTrackInfo(Long id) {
        //1.获取被删除声音记录 得到：音频唯一标识（用于删除点播平台）、声音序号（用于更新其他声音序号）
        TrackInfo trackInfo = trackInfoMapper.selectById(id);
        Integer orderNum = trackInfo.getOrderNum();
        String mediaFileId = trackInfo.getMediaFileId();
        Long albumId = trackInfo.getAlbumId();

        //2.删除声音记录，同时更新专辑包含声音数量
        trackInfoMapper.deleteById(id);
        albumInfoMapper.update(
                null,
                new LambdaUpdateWrapper<AlbumInfo>().eq(AlbumInfo::getId, albumId)
                        .setSql("include_track_count = include_track_count -1")
        );

        //3.更新其他声音序号，确保声音序号连续
        trackInfoMapper.update(
                null,
                new LambdaUpdateWrapper<TrackInfo>().eq(TrackInfo::getAlbumId, albumId)
                        .gt(TrackInfo::getOrderNum, orderNum)
                        .setSql("order_num = order_num -1")
        );

        //4.删除声音统计信息
        trackStatMapper.delete(
                new LambdaQueryWrapper<TrackStat>().eq(TrackStat::getTrackId, id)
        );

        //5.从点播平台删除文件
        vodService.deleteMedia(mediaFileId);
    }

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 根据专辑ID分页查询声音列表包含统计信息（动态渲染付费标识）
     *
     * @param albumId 专辑ID
     * @param userId  用户ID
     * @return 分页对象
     */
    @Override
    public IPage<AlbumTrackListVo> findAlbumTrackPage(IPage<AlbumTrackListVo> pageInfo, Long albumId, Long userId) {
        //1.调用持久层执行动态SQL查询声音列表-付费标识都为:false
        pageInfo = trackInfoMapper.findAlbumTrackPage(pageInfo, albumId);
        //2. 根据专辑ID查询付费类型
        AlbumInfo albumInfo = albumInfoMapper.selectById(albumId);
        //付费类型: 0101-免费、0102-vip免费、0103-付费
        String payType = albumInfo.getPayType();
        //免费试听集数
        Integer tracksForFree = albumInfo.getTracksForFree();
        // 基于登录状态、用户身份、用户购买情况，动态修改付费标识
        //3.处理未登录情形
        if (userId == null) {
            //3.1 付费类型是VIP免费或付费
            if (ALBUM_PAY_TYPE_VIPFREE.equals(payType) | ALBUM_PAY_TYPE_REQUIRE.equals(payType)) {
                //3.2 除了 试听以外 其他声音都应将付费标识改为true
                pageInfo.getRecords().stream()
                        .filter(track -> track.getOrderNum() > tracksForFree)
                        .forEach(track -> track.setIsShowPaidMark(true));
            }
        } else {
            //4. 处理已登录情形
            //4.1 远程调用"用户服务"获取用户基本信息 得到身份信息
            Boolean isVIP = false;
            UserInfoVo userInfoVo = userFeignClient.getUserInfoVo(userId).getData();
            Assert.notNull(userInfoVo, "用户{}不存在", userId);
            if (userInfoVo.getIsVip().intValue() == 1
                    && userInfoVo.getVipExpireTime().after(new Date())) {
                //会员标识为1，且会员过期时间晚于当前时间
                isVIP = true;
            }

            //4.2 是否需要进一步检查声音购买状态
            Boolean isNeedCheckPayStatus = false;

            //4.2.1 如果是 普通用户 查看 付费类型为：“VIP免费”专辑 默认无权益播放
            if (!isVIP && ALBUM_PAY_TYPE_VIPFREE.equals(payType)) {
                isNeedCheckPayStatus = true;
            }
            //4.2.2 如果是 付费类型为：“付费” 所有用户默认无权益播放
            if (ALBUM_PAY_TYPE_REQUIRE.equals(payType)) {
                isNeedCheckPayStatus = true;
            }

            //4.3 如果需要检查购买状态，则远程调用"用户服务"获取声音购买状态得到Map<Long, Integer>
            if (isNeedCheckPayStatus) {
                //4.3.1 找出本页中非试听声音ID列表作为检查购买状态声音ID列表
                List<Long> needChekPayStatusTrackIdList = pageInfo.getRecords().stream()
                        //排除掉试听
                        .filter(track -> track.getOrderNum() > tracksForFree)
                        //获取声音ID
                        .map(AlbumTrackListVo::getTrackId)
                        //收集声音ID
                        .collect(Collectors.toList());
                //4.3.2 远程调用"用户服务"获取声音购买状态
                Map<Long, Integer> payStatusMap = userFeignClient.userIsPaidTrack(userId, albumId, needChekPayStatusTrackIdList).getData();
                //4.4 处理当前页中声音购买状态标识，如果未购买将购买标识设置为True，反之采用默认值false
                pageInfo.getRecords().stream()
                        //试听声音记录不需要判断，付费标识保留默认false
                        .filter(track -> track.getOrderNum() > tracksForFree)
                        .forEach(track -> track.setIsShowPaidMark(payStatusMap.get(track.getTrackId()).intValue() == 0));
                //.forEach(track -> {
                //    Integer payStatus = payStatusMap.get(track.getTrackId());
                //    if (payStatus.intValue() == 0) {
                //        //未购买声音，将付费标识设置为：True
                //        track.setIsShowPaidMark(true);
                //    }
                //});
            }
        }
        return pageInfo;
    }

    /**
     * 增量更新声音统计数值
     *
     * @param trackStatMqVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTrackStat(TrackStatMqVo trackStatMqVo) {
        //1.更新声音统计表
        trackStatMapper.update(
                null,
                new LambdaUpdateWrapper<TrackStat>()
                        .eq(TrackStat::getTrackId, trackStatMqVo.getTrackId())
                        .eq(TrackStat::getStatType, trackStatMqVo.getStatType())
                        .setSql("stat_num = stat_num + " + trackStatMqVo.getCount())
        );
        //2.如果统计类型是：播放量、评论量 所属专辑统计信息需更新
        if (TRACK_STAT_PLAY.equals(trackStatMqVo.getStatType())) {
            //2.1. 更新专辑播放量
            albumStatMapper.update(
                    null,
                    new LambdaUpdateWrapper<AlbumStat>()
                            .eq(AlbumStat::getAlbumId, trackStatMqVo.getAlbumId())
                            .eq(AlbumStat::getStatType, ALBUM_STAT_PLAY)
                            .setSql("stat_num = stat_num + " + trackStatMqVo.getCount())
            );
        }
        if (TRACK_STAT_COMMENT.equals(trackStatMqVo.getStatType())) {
            //2.2. 更新专辑评论量
            albumStatMapper.update(
                    null,
                    new LambdaUpdateWrapper<AlbumStat>()
                            .eq(AlbumStat::getAlbumId, trackStatMqVo.getAlbumId())
                            .eq(AlbumStat::getStatType, ALBUM_STAT_COMMENT)
                            .setSql("stat_num = stat_num + " + trackStatMqVo.getCount())
            );
        }
    }


}
