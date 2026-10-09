package com.atguigu.tingshu.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "UserListenProcessListVo")
public class UserListenProcessListVo {

	@Schema(description = "id")
	private String id;

	@Schema(description = "专辑id")
	private Long albumId;

	@Schema(description = "标题")
	private String albumTitle;

	@Schema(description = "声音id")
	private Long trackId;

	@Schema(description = "标题")
	private String trackTitle;

	@Schema(description = "专辑封面原图，尺寸不固定，最大尺寸为960*960（像素）")
	private String coverUrl;

	@Schema(description = "声音媒体时长，单位秒")
	private BigDecimal mediaDuration;

	@Schema(description = "相对于音频开始位置的播放跳出位置，单位为秒。比如当前音频总时长60s，本次播放到音频第25s处就退出或者切到下一首，那么break_second就是25")
	private BigDecimal breakSecond;

	@Schema(description = "播放比例")
	private String playRate;

}