package com.atguigu.tingshu.album.config;


import com.qcloud.vod.VodUploadClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.ims.v20201229.ImsClient;
import com.tencentcloudapi.tms.v20201229.TmsClient;
import com.tencentcloudapi.vod.v20180717.VodClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vod") //读取节点
@Data
public class VodConstantProperties {

    private Integer appId;
    private String secretId;
    private String secretKey;
    //https://cloud.tencent.com/document/api/266/31756#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8
    private String region;
    private String tempPath;


    /**
     * 用于文件上传客户端对象
     *
     * @return
     */
    @Bean
    public VodUploadClient vodUploadClient() {
        return new VodUploadClient(secretId, secretKey);
    }


    @Bean
    public Credential credential() {
        return new Credential(secretId, secretKey);
    }

    @Bean
    public VodClient vodClient() {
        return new VodClient(credential(), region);
    }


    /**
     * 文本审核客户端对象
     * @return
     */
    @Bean
    public TmsClient tmsClient() {
        return new TmsClient(credential(), region);
    }

    /**
     * 图片审核客户端对象
     * @return
     */
    @Bean
    public ImsClient imsClient() {
        return new ImsClient(credential(), region);
    }

}
