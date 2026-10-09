package com.atguigu.tingshu.album.service.impl;

import com.atguigu.tingshu.album.config.VodConstantProperties;
import com.atguigu.tingshu.album.service.VodService;
import com.atguigu.tingshu.common.util.UploadFileUtil;
import com.atguigu.tingshu.vo.album.TrackMediaInfoVo;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@Slf4j
@Service
public class VodServiceImpl implements VodService {

    @Autowired
    private VodConstantProperties vodConstantProperties;

    @Autowired
    private VodUploadClient vodUploadClient;

    @Autowired
    private VodClient vodClient;

    /**
     * 文件上传，将音视频文件上传到点播平台
     *
     * @param file 文件
     * @return {mediaFileId:"文件唯一标识",mediaUrl:"播放地址"}
     */
    @Override
    public Map<String, String> uploadTrack(MultipartFile file) {
        try {
            //1.将接收到文件保存到当前服务器指定 临时目录下
            String filePath = UploadFileUtil.uploadTempPath(vodConstantProperties.getTempPath(), file);
            //2.调用点播平台SDK上传文件
            //2.1 构造上传请求对象
            VodUploadRequest request = new VodUploadRequest();
            request.setMediaFilePath(filePath);
            //2.2 调用上传方法，传入接入点地域及上传请求。
            VodUploadResponse response = vodUploadClient.upload(vodConstantProperties.getRegion(), request);

            //3.封装返回结果
            if (response != null) {
                return Map.of("mediaFileId", response.getFileId(), "mediaUrl", response.getMediaUrl());
            }
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * 从点播平台获取媒体文件详情，得到媒体文件详情
     *
     * @param mediaFileId 文件唯一标识
     * @return
     */
    @Override
    public TrackMediaInfoVo getMediaInfo(String mediaFileId) {
        try {
            //1.实例化一个请求对象,每个接口都会对应一个request对象
            DescribeMediaInfosRequest req = new DescribeMediaInfosRequest();
            String[] fileIds1 = {mediaFileId};
            req.setFileIds(fileIds1);
            //2.发起请求获取音频文件详细列表
            DescribeMediaInfosResponse resp = vodClient.DescribeMediaInfos(req);
            //3.解析结果
            if (resp != null) {
                MediaInfo[] mediaInfoSet = resp.getMediaInfoSet();
                if (mediaInfoSet != null && mediaInfoSet.length > 0) {
                    MediaInfo mediaInfo = mediaInfoSet[0];
                    //3.1 获取基本信息 音频文件类型
                    String type = mediaInfo.getBasicInfo().getType();
                    //3.2 获取元信息 时长、大小
                    MediaMetaData metaData = mediaInfo.getMetaData();
                    Float audioDuration = metaData.getAudioDuration();
                    Long size = metaData.getSize();
                    //3.封装结果
                    TrackMediaInfoVo vo = new TrackMediaInfoVo();
                    vo.setDuration(audioDuration);
                    vo.setSize(size);
                    vo.setType(type);
                    return vo;
                }
            }
        } catch (TencentCloudSDKException e) {
            log.error("获取媒体文件详情失败", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteMedia(String mediaFileId) {
        try {
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(mediaFileId);
            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            vodClient.DeleteMedia(req);
        } catch (TencentCloudSDKException e) {
            log.error("删除媒体文件失败", e);
        }
    }
}
