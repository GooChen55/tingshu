package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "BaseDic")
@TableName("base_dic")
public class BaseDic extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "code")
	@TableField("code")
	private String code;

	@Schema(description = "编码名称")
	@TableField("dic_name")
	private String dicName;

	@Schema(description = "父编号")
	@TableField("parent_code")
	private String parentCode;

}