package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "声音信息")
public class TrackInfoVo {

	@Positive(message = "专辑Id不能为空")
	@Schema(description = "专辑id", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long albumId;

	@NotEmpty(message = "媒体文件Id不能为空")
	@Schema(description = "媒体文件的唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
	private String mediaFileId;

	@NotEmpty(message = "媒体文件地址")
	@Schema(description = "媒体文件地址", requiredMode = Schema.RequiredMode.REQUIRED)
	private String mediaUrl;

	@NotEmpty(message = "声音标题不能为空")
	@Schema(description = "声音标题", requiredMode = Schema.RequiredMode.REQUIRED)
	private String trackTitle;

	@Schema(description = "声音简介")
	private String trackIntro;

	@Schema(description = "声音简介，富文本")
	private String trackRichIntro;

	@Schema(description = "声音封面图url")
	private String coverUrl;

	@Schema(description = "是否公开：0-否 1-是")
	private String isOpen;

}
