package com.atguigu.tingshu.vo.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class AlbumInfoIndexVo {

    // 专辑Id
    @Schema(description = "专辑id")
    private Long id;

    @Schema(description = "专辑标题")
    private String albumTitle;

    @Schema(description = "专辑简介")
    private String albumIntro;

    @Schema(description = "主播名称")
    private String announcerName;

    @Schema(description = "专辑封面")
    private String coverUrl;

    @Schema(description = "专辑包含声音总数")
    private Integer includeTrackCount;

    @Schema(description = "专辑是否完结：0-否；1-完结")
    private String isFinished;

    @Schema(description = "付费类型: 0101-免费、0102-vip免费、0103-付费")
    private String payType;

    @Schema(description = "专辑创建时间")
    private Date createTime; //

    //播放量
    @Schema(description = "播放量")
    private Integer playStatNum = 0;

    //订阅量
    @Schema(description = "订阅量")
    private Integer subscribeStatNum = 0;

    //购买量
    @Schema(description = "购买量")
    private Integer buyStatNum = 0;

    //评论数
    @Schema(description = "评论数")
    private Integer commentStatNum = 0;

}
