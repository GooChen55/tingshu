package com.atguigu.tingshu.vo.live;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "LiveRoom")
public class LiveRoomVo {


	@Schema(description = "直播间封面")
	private String coverUrl;

	@Schema(description = "直播标题")
	private String liveTitle;

	@Schema(description = "直播间标签id")
	private String tagId;

	@Schema(description = "过期时间")
	private Date expireTime;

	@Schema(description = "经度")
	private String longitude;

	@Schema(description = "纬度")
	private String latitude;

	@Schema(description = "位置")
	private String location;

}