package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "BaseAttribute")
@TableName("base_attribute")
public class BaseAttribute extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "一级分类id")
	@TableField("category1_id")
	private Long category1Id;

	@Schema(description = "属性显示名称")
	@TableField("attribute_name")
	private String attributeName;

	@TableField(exist = false)
	private List<BaseAttributeValue> attributeValueList;

}