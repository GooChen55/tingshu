package com.atguigu.tingshu.album.service;

import org.springframework.web.multipart.MultipartFile;

public interface AuditService {

    /**
     * 对文本进行审核
     *
     * @param content 待审核文本
     * @return 审核处置意见 Block: 建议直接做违规处置，Review: 建议人工二次确认，Pass: 未识别到风险
     */
    String audit_text(String content);


    /**
     * 对图片进行审核
     *
     * @param imageFile 待审核图片文件
     * @return 审核处置意见 Block: 建议直接做违规处置，Review: 建议人工二次确认，Pass: 未识别到风险
     */
    String audit_image(MultipartFile imageFile);


    /**
     * 启动音频审核任务
     * @param mediaFieId 媒体文件 ID，即该文件在云点播上的全局唯一标识符
     * @return 任务 ID
     */
    String startReviewTask(String mediaFieId);


    /**
     * 根据审核任务ID查询审核处置意见
     * @param taskId 审核任务ID
     * @return
     * pass：建议通过；
     * review：建议复审；
     * block：建议封禁。
     */
    String getReviewTaskResult(String taskId);

}
