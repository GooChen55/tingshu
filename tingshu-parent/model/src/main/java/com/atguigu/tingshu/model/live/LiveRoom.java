package com.atguigu.tingshu.model.live;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "LiveRoom")
@TableName("live_room")
public class LiveRoom extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "直播间封面")
	@TableField("cover_url")
	private String coverUrl;

	@Schema(description = "直播标题")
	@TableField("live_title")
	private String liveTitle;

	@Schema(description = "当前直播场次的总访问人次")
	@TableField("view_count")
	private Integer viewCount;

	@Schema(description = "直播状态:1-直播正在进行，2-直播结束")
	@TableField("status")
	private String status;

	@Schema(description = "直播间标签id")
	@TableField("tag_id")
	private String tagId;

	@Schema(description = "直播应用名称")
	@TableField("app_name")
	private String appName;

	@Schema(description = "直播流名称")
	@TableField("stream_name")
	private String streamName;

	@Schema(description = "过期时间")
	@TableField("expire_time")
	private Date expireTime;

	@Schema(description = "推流地址")
	@TableField("push_url")
	private String pushUrl;

	@Schema(description = "播放地址")
	@TableField("play_url")
	private String playUrl;

	@Schema(description = "经度")
	@TableField("longitude")
	private String longitude;

	@Schema(description = "纬度")
	@TableField("latitude")
	private String latitude;

	@Schema(description = "位置")
	@TableField("location")
	private String location;

}