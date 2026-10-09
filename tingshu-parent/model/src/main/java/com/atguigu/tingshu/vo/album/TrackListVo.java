package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "用户声音列表信息")
public class TrackListVo {

	//@Schema(description = "专辑id")
	//private Long albumId;

	//@Schema(description = "标题")
	//private String albumTitle;

	@Schema(description = "声音id")
	private Long trackId;

	@Schema(description = "标题")
	private String trackTitle;

	@Schema(description = "专辑封面原图，尺寸不固定，最大尺寸为960*960（像素）")
	private String coverUrl;

	@Schema(description = "声音媒体时长，单位秒")
	private BigDecimal mediaDuration;

	@Schema(description = "状态")
	private String status;

	@Schema(description = "播放量")
	private Integer playStatNum;

	@Schema(description = "订阅量")
	private Integer collectStatNum;

	@Schema(description = "购买量")
	private Integer praiseStatNum;

	@Schema(description = "评论数")
	private Integer commentStatNum;

}
