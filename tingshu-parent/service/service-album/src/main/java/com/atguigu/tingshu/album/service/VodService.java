package com.atguigu.tingshu.album.service;

import com.atguigu.tingshu.vo.album.TrackMediaInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VodService {

    /**
     * 文件上传，将音视频文件上传到点播平台
     *
     * @param file 文件
     * @return {mediaFileId:"文件唯一标识",mediaUrl:"播放地址"}
     */
    Map<String, String> uploadTrack(MultipartFile file);

    /**
     * 从点播平台获取媒体文件详情，得到媒体文件详情
     * @param mediaFileId 文件唯一标识
     * @return
     */
    TrackMediaInfoVo getMediaInfo(String mediaFileId);

    void deleteMedia(String mediaFileId);
}
