package com.atguigu.tingshu.query.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "声音信息")
public class TrackInfoQuery {

	@Schema(description = "标题")
	private String trackTitle;

	@Schema(description = "状态")
	private String status;

	@Schema(description = "用户id")
	private Long userId;
}