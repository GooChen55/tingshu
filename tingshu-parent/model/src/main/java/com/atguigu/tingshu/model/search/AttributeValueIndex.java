package com.atguigu.tingshu.model.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Schema(description = "专辑属性值")
public class AttributeValueIndex {

	@Field(type = FieldType.Long)
	private Long attributeId;

	@Field(type = FieldType.Long)
	private Long valueId;

}