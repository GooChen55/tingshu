package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "TrackStatMqVo")
public class TrackStatMqVo implements Serializable {

	@Schema(description = "业务编号：消费者端去重使用")
	private String businessNo;

	@Schema(description = "专辑id")
	private Long albumId;

	@Schema(description = "声音id")
	private Long trackId;

	@Schema(description = "统计类型")
	private String statType;

	@Schema(description = "更新数目")
	private Integer count;

}
