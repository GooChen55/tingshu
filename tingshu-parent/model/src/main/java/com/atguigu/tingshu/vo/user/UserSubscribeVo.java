package com.atguigu.tingshu.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "用户订阅")
public class UserSubscribeVo {

	@Schema(description = "专辑ID")
	private Long albumId;

	@Schema(description = "创建时间")
	private Date createTime;

	@Schema(description = "标题")
	private String albumTitle;

	@Schema(description = "专辑封面原图，尺寸不固定，最大尺寸为960*960（像素）")
	private String coverUrl;

	@Schema(description = "专辑包含声音总数")
	private Integer includeTrackCount;

	@Schema(description = "专辑是否完结：0-否；1-完结；")
	private String isFinished;

}