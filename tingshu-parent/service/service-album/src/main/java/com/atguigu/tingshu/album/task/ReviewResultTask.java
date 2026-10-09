package com.atguigu.tingshu.album.task;

import cn.hutool.core.collection.CollUtil;
import com.atguigu.tingshu.album.mapper.TrackInfoMapper;
import com.atguigu.tingshu.album.service.AuditService;
import com.atguigu.tingshu.common.constant.SystemConstant;
import com.atguigu.tingshu.model.album.TrackInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: atguigu
 * @create: 2026-06-06 15:40
 */
@Slf4j
@Component
public class ReviewResultTask {

    @Autowired
    private TrackInfoMapper trackInfoMapper;

    @Autowired
    private AuditService auditService;

    /**
     * 定时任务：处理审核结果
     * 扫描处于审核中状态声音记录，根据声音记录中的审核任务ID，查询审核建议
     * cron表达式：秒 分 时 日 月 周 【年】
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void handleReviewResult() {
        //log.info("处理审核结果");
        List<TrackInfo> trackInfoList = trackInfoMapper.selectList(
                new LambdaQueryWrapper<TrackInfo>()
                        .eq(TrackInfo::getStatus, SystemConstant.TRACK_STATUS_REVIEWING)
                        .select(TrackInfo::getId, TrackInfo::getReviewTaskId)
        );
        if (CollUtil.isNotEmpty(trackInfoList)) {
            for (TrackInfo trackInfo : trackInfoList) {
                String suggestion = auditService.getReviewTaskResult(trackInfo.getReviewTaskId());
                if (StringUtils.isNotBlank(suggestion)) {
                    if ("block".equals(suggestion)) {
                        trackInfo.setStatus(SystemConstant.TRACK_STATUS_NO_PASS);
                    } else if ("review".equals(suggestion)) {
                        trackInfo.setStatus(SystemConstant.TRACK_STATUS_MANUAL);
                    } else if ("pass".equals(suggestion)) {
                        trackInfo.setStatus(SystemConstant.TRACK_STATUS_PASS);
                    }
                    trackInfoMapper.updateById(trackInfo);
                }
            }
        }
    }
}
