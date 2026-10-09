package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "BaseCategory3")
@TableName("base_category3")
public class BaseCategory3 extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "三级分类名称")
	@TableField("name")
	private String name;

	@Schema(description = "二级分类编号")
	@TableField("category2_id")
	private Long category2Id;

	@Schema(description = "排序")
	@TableField("order_num")
	private Integer orderNum;

	@Schema(description = "是否置顶")
	@TableField("is_top")
	private Integer isTop;
}