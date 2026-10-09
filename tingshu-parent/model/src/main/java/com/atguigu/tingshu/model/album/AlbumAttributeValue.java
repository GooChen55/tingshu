package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "专辑属性值")
public class AlbumAttributeValue extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "专辑id")
	private Long albumId;

	@NotNull(message = "属性id不能为空")
	@Schema(description = "属性id")
	private Long attributeId;

	@NotNull(message = "属性值id不能为空")
	@Schema(description = "属性值id")
	private Long valueId;

	@Schema(description = "属性名称")
	@TableField(exist = false)
	private String attributeName;

	@Schema(description = "属性值名称")
	@TableField(exist = false)
	private String valueName;

}