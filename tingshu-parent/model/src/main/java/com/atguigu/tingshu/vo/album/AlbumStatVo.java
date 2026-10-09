package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "专辑统计信息")
public class AlbumStatVo {

	@Schema(description = "专辑id")
	private Long albumId;

	@Schema(description = "播放量")
	private Integer playStatNum;

	@Schema(description = "订阅量")
	private Integer subscribeStatNum;

	@Schema(description = "购买量")
	private Integer buyStatNum;

	@Schema(description = "评论数")
	private Integer commentStatNum;
}