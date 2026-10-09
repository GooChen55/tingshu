package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "BaseCategory1")
@TableName("base_category1")
public class BaseCategory1 extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "分类名称")
	@TableField("name")
	private String name;

	@Schema(description = "排序")
	@TableField("order_num")
	private Integer orderNum;

}