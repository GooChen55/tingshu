package com.atguigu.tingshu.vo.album;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "专辑属性值")
public class AlbumAttributeValueVo {

	@NotNull(message = "属性id不能为空")
	@Schema(description = "属性id")
	private Long attributeId;

	@NotNull(message = "属性值id不能为空")
	@Schema(description = "属性值id")
	private Long valueId;

}