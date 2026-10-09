//
//
package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * BaseCategoryView
 * </p>
 *
 * @author atguigu
 */
@Data
@Schema(description = "分类视图")
@TableName("base_category_view")
public class BaseCategoryView extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "一级分类编号")
	@TableField("category1_id")
	private Long category1Id;

	@Schema(description = "一级分类名称")
	@TableField("category1_name")
	private String category1Name;

	@Schema(description = "二级分类编号")
	@TableField("category2_id")
	private Long category2Id;

	@Schema(description = "二级分类名称")
	@TableField("category2_name")
	private String category2Name;

	@Schema(description = "三级分类编号")
	@TableField("category3_id")
	private Long category3Id;

	@Schema(description = "三级分类名称")
	@TableField("category3_name")
	private String category3Name;

}

