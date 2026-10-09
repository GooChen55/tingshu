package com.atguigu.tingshu.album.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.atguigu.tingshu.album.config.MinioConstantProperties;
import com.atguigu.tingshu.album.service.AuditService;
import com.atguigu.tingshu.album.service.FileUploadService;
import com.atguigu.tingshu.common.execption.GuiguException;
import com.atguigu.tingshu.common.result.ResultCodeEnum;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: atguigu
 * @create: 2026-06-05 10:15
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConstantProperties minioConstantProperties;

    @Autowired
    private AuditService auditService;

    /**
     * 图片（专辑封面、用户头像）文件上传
     *
     * @param file 文件
     * @return 在线地址，用于前端预览
     */
    @Override
    public String fileUpload(MultipartFile file) {
        try {
            //1.业务校验 验证格式、大小要求在900*900
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new GuiguException(500, "上传图片格式错误！");
            }
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();

            if (height > 900 || width > 900) {
                throw new GuiguException(500, "上传图片尺寸过大！");
            }

            //2.验证图片内容是否合规
            String suggestion = auditService.audit_image(file);
            if (StringUtils.isNotBlank(suggestion)) {
                if ("block".equals(suggestion) || "review".equals(suggestion)) {
                    throw new GuiguException(500, "图片存在违规");
                }
            }

            //3.将图片文件上传到MinIO
            //3.1 生成唯一文件名 格式=日期/唯一文件名.后缀
            String folderName = DateUtil.today();
            String fileName = IdUtil.randomUUID();
            String extName = FileNameUtil.extName(file.getOriginalFilename());
            String objName = "/" + folderName + "/" + fileName + "." + extName;

            //3.2 调用minioClient上传文件
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioConstantProperties.getBucketName()).object(objName).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            //4.拼接图片的访问路径
            return minioConstantProperties.getEndpointUrl() + "/" + minioConstantProperties.getBucketName() + objName;
        } catch (GuiguException e) {
            log.error("上传图片失败！", e);
            throw new GuiguException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("上传图片失败！", e);
            throw new RuntimeException(e);
        }
    }
}
