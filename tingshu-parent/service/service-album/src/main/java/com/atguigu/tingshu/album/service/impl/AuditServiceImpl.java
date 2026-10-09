package com.atguigu.tingshu.album.service.impl;

import cn.hutool.core.codec.Base64;
import com.atguigu.tingshu.album.service.AuditService;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ims.v20201229.ImsClient;
import com.tencentcloudapi.ims.v20201229.models.ImageModerationRequest;
import com.tencentcloudapi.ims.v20201229.models.ImageModerationResponse;
import com.tencentcloudapi.tms.v20201229.TmsClient;
import com.tencentcloudapi.tms.v20201229.models.TextModerationRequest;
import com.tencentcloudapi.tms.v20201229.models.TextModerationResponse;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: atguigu
 * @create: 2026-06-06 14:21
 */
@Slf4j
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private TmsClient tmsClient;

    @Autowired
    private ImsClient imsClient;

    @Autowired
    private VodClient vodClient;

    /**
     * 对文本进行审核
     *
     * @param content 待审核文本 文本内容小于1W长度
     * @return 审核处置意见 Block: 建议直接做违规处置，Review: 建议人工二次确认，Pass: 未识别到风险
     */
    @Override
    public String audit_text(String content) {
        try {
            //1.实例化一个请求对象
            TextModerationRequest req = new TextModerationRequest();
            //2.对文本进行Base64编码
            req.setContent(Base64.encode(content));
            //3.调用文本审核接口
            TextModerationResponse resp = tmsClient.TextModeration(req);
            //4.获取处置意见
            if (resp != null) {
                String suggestion = resp.getSuggestion();
                log.info("文本：{}，处置意见：{}", content, suggestion);
                return suggestion.toLowerCase();
            }
        } catch (TencentCloudSDKException e) {
            log.error("文本内容审核异常", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 对图片进行审核
     *
     * @param imageFile 待审核图片文件
     * @return 审核处置意见 Block: 建议直接做违规处置，Review: 建议人工二次确认，Pass: 未识别到风险
     */
    @Override
    public String audit_image(MultipartFile imageFile) {
        try {
            //1.实例化一个请求对象
            ImageModerationRequest req = new ImageModerationRequest();
            //2.对图片文件进行Base64编码
            req.setFileContent(Base64.encode(imageFile.getInputStream()));
            //3.执行图片同步审核
            ImageModerationResponse resp = imsClient.ImageModeration(req);
            //4.获取处置意见
            if (resp != null) {
                String suggestion = resp.getSuggestion();
                log.info("对图片{}，审核结果：{}", imageFile.getOriginalFilename(), suggestion);
                return suggestion.toLowerCase();
            }
        } catch (IOException e) {
            log.error("图片审核异常：", e);
            throw new RuntimeException(e);
        } catch (TencentCloudSDKException e) {
            log.error("图片审核异常：", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 启动音频审核任务
     *
     * @param mediaFieId 媒体文件 ID，即该文件在云点播上的全局唯一标识符
     * @return 任务 ID
     */
    @Override
    public String startReviewTask(String mediaFieId) {
        try {
            //1.实例化一个请求对象
            ReviewAudioVideoRequest req = new ReviewAudioVideoRequest();
            req.setFileId(mediaFieId);
            //2.返回的resp是一个ReviewAudioVideoResponse的实例，与请求对象对应
            ReviewAudioVideoResponse resp = vodClient.ReviewAudioVideo(req);
            //3.获取发起审核任务ID
            if (resp != null) {
                return resp.getTaskId();
            }
        } catch (TencentCloudSDKException e) {
            log.error("发起音视频审核失败", e);
            throw new RuntimeException(e);
        }
        return "";
    }

    /**
     * 根据审核任务ID查询审核处置意见
     *
     * @param taskId 审核任务ID
     * @return pass：建议通过；
     * * review：建议复审；
     * * block：建议封禁。
     */
    @Override
    public String getReviewTaskResult(String taskId) {
        try {
            //1.实例化一个请求对象
            DescribeTaskDetailRequest req = new DescribeTaskDetailRequest();
            req.setTaskId(taskId);
            //2.返回的resp是一个DescribeTaskDetailResponse的实例，与请求对象对应
            DescribeTaskDetailResponse resp = vodClient.DescribeTaskDetail(req);
            //3.解析结果
            if (resp != null) {
                //3.1 确保任务已结束 且 任务类型是音视频审核任务
                String status = resp.getStatus();
                if ("FINISH".equals(status) && "ReviewAudioVideo".equals(resp.getTaskType())) {
                    //3.2 获取音视频审核任务信息
                    ReviewAudioVideoTask reviewAudioVideoTask = resp.getReviewAudioVideoTask();
                    //3.2.1 任务状态，取值：FINISH：已完成。
                    if ("FINISH".equals(reviewAudioVideoTask.getStatus())) {
                        //音视频审核任务的输出
                        ReviewAudioVideoTaskOutput output = reviewAudioVideoTask.getOutput();
                        //获取处置意见
                        String suggestion = output.getSuggestion();
                        log.info("音视频审核任务：{}， 审核结果：{}", taskId, suggestion);
                        return suggestion.toLowerCase();
                    }
                }
            }
        } catch (TencentCloudSDKException e) {
            log.error("根据审核任务ID查询审核处置意见异常：", e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
