package com.atguigu.tingshu.album.api;

import com.atguigu.tingshu.album.service.FileUploadService;
import com.atguigu.tingshu.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "上传管理接口")
@RestController
@RequestMapping("api/album")
public class FileUploadApiController {


    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 图片（专辑封面、用户头像）文件上传
     * @param file 文件
     * @return 在线地址，用于前端预览
     */
    @Operation(summary = "图片（专辑封面、用户头像）文件上传")
    @PostMapping("/fileUpload")
    public Result<String> fileUpload(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.fileUpload(file);
        return Result.ok(fileUrl);
    }

}
