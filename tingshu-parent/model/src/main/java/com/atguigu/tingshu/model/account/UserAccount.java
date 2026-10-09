package com.atguigu.tingshu.model.account;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "UserAccount")
@TableName("user_account")
public class UserAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "账户总金额")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@Schema(description = "锁定金额")
	@TableField("lock_amount")
	private BigDecimal lockAmount;

	@Schema(description = "可用金额")
	@TableField("available_amount")
	private BigDecimal availableAmount;

	@Schema(description = "总收入")
	@TableField("total_income_amount")
	private BigDecimal totalIncomeAmount;

	@Schema(description = "总支出")
	@TableField("total_pay_amount")
	private BigDecimal totalPayAmount;

}