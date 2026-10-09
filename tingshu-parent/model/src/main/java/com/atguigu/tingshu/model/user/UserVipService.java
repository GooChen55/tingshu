package com.atguigu.tingshu.model.user;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "UserVipService")
@TableName("user_vip_service")
public class UserVipService extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "订单号")
	@TableField("order_no")
	private String orderNo;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "开始生效日期")
	@TableField("start_time")
	private Date startTime;

	@Schema(description = "到期时间")
	@TableField("expire_time")
	private Date expireTime;

	@Schema(description = "是否自动续费")
	@TableField("is_auto_renew")
	private Integer isAutoRenew;

	@Schema(description = "下次自动续费时间")
	@TableField("next_renew_time")
	private Date nextRenewTime;

}