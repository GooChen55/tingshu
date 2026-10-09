package com.atguigu.tingshu.query.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "专辑信息")
public class AlbumInfoQuery {

	@Schema(description = "标题")
	private String albumTitle;

	@Schema(description = "状态")
	private String status;

	@Schema(description = "用户id")
	private Long userId;
}