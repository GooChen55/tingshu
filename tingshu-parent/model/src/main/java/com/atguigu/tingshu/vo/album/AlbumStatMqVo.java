package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "AlbumStatMqVo")
public class AlbumStatMqVo {

	@Schema(description = "业务编号：去重使用")
	private String businessNo;

	@Schema(description = "专辑id")
	private Long albumId;

	@Schema(description = "统计类型")
	private String statType;

	@Schema(description = "更新数目")
	private Integer count;

}