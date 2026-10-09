package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "声音媒体信息")
public class TrackMediaInfoVo {

	@Schema(description = "音频文件大小，单位字节")
	private Long size;

	@Schema(description = "声音时长，单位秒")
	private Float duration;

	//@Schema(description = "声音播放地址")
	//private String mediaUrl;

	@Schema(description = "声音媒体类型")
	private String type;
}
