package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "TrackInfo")
@TableName("track_info")
public class TrackInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "专辑id")
	@TableField("album_id")
	private Long albumId;

	@Schema(description = "声音标题")
	@TableField("track_title")
	private String trackTitle;

	@Schema(description = "声音在专辑中的排序值，从0开始依次递增，值越小排序越前")
	@TableField("order_num")
	private Integer orderNum;

	@Schema(description = "声音简介")
	@TableField("track_intro")
	private String trackIntro;

	@Schema(description = "声音简介，富文本")
	@TableField("track_rich_intro")
	private String trackRichIntro;

	@Schema(description = "声音封面图url")
	@TableField("cover_url")
	private String coverUrl;

	@Schema(description = "声音媒体时长，单位秒")
	@TableField("media_duration")
	private BigDecimal mediaDuration;

	@Schema(description = "媒体文件的唯一标识")
	@TableField("media_file_id")
	private String mediaFileId;

	@Schema(description = "媒体播放地址")
	@TableField("media_url")
	private String mediaUrl;

	@Schema(description = "音频文件大小，单位字节")
	@TableField("media_size")
	private Long mediaSize;

	@Schema(description = "声音媒体类型")
	@TableField("media_type")
	private String mediaType;

	@Schema(description = "声音来源，1-用户原创，2-上传")
	@TableField("source")
	private String source;

	@Schema(description = "状态")
	@TableField("status")
	private String status;

	@Schema(description = "是否公开：0-否 1-是")
	@TableField("is_open")
	private String isOpen;
	
	@Schema(description = "发起审核任务ID")
	@TableField("review_task_id")
	private String reviewTaskId;
}