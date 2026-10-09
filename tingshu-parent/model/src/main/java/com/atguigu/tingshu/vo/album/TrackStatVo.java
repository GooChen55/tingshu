package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户声音统计信息")
public class TrackStatVo {


	@Schema(description = "播放量")
	private Integer playStatNum;

	@Schema(description = "订阅量")
	private Integer collectStatNum;

	@Schema(description = "购买量")
	private Integer praiseStatNum;

	@Schema(description = "评论数")
	private Integer commentStatNum;

}
