package com.atguigu.tingshu.model.account;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "UserAccountDetail")
@TableName("user_account_detail")
public class UserAccountDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "交易标题")
	@TableField("title")
	private String title;

	@Schema(description = "交易类型：1201-充值 1202-锁定 1203-解锁 1204-消费")
	@TableField("trade_type")
	private String tradeType;

	@Schema(description = "金额")
	@TableField("amount")
	private BigDecimal amount;

	@Schema(description = "订单编号")
	@TableField("order_no")
	private String orderNo;

}