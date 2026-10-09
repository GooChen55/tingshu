package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "BaseCategory2")
@TableName("base_category2")
public class BaseCategory2 extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "二级分类名称")
	@TableField("name")
	private String name;

	@Schema(description = "一级分类编号")
	@TableField("category1_id")
	private Long category1Id;

	@Schema(description = "orderNum")
	@TableField("order_num")
	private Integer orderNum;

}