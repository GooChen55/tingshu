package com.atguigu.tingshu.model.user;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UserStat")
@TableName("user_stat")
public class UserStat extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "统计类型")
	@TableField("stat_type")
	private Integer statType;

	@Schema(description = "统计数目")
	@TableField("stat_num")
	private Integer statNum;

}