package com.atguigu.tingshu.album.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    /**
     * 图片（专辑封面、用户头像）文件上传
     * @param file 文件
     * @return 在线地址，用于前端预览
     */
    String fileUpload(MultipartFile file);
}
