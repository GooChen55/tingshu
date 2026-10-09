package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "用户专辑声音列表信息")
public class AlbumTrackListVo {

	@Schema(description = "声音id")
	private Long trackId;

	@Schema(description = "标题")
	private String trackTitle;

	@Schema(description = "声音媒体时长，单位秒")
	private BigDecimal mediaDuration;

	@Schema(description = "排序")
	private Integer orderNum;

	@Schema(description = "播放量")
	private Integer playStatNum;

	@Schema(description = "评论数")
	private Integer commentStatNum;

	@Schema(description = "发布时间")
	private Date createTime;

	@Schema(description = "是否显示付费标识，isShowPiadMark=true，则显示付费标识")
	private Boolean isShowPaidMark = false;

}
