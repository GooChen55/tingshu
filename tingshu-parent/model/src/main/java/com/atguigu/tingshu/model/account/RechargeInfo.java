package com.atguigu.tingshu.model.account;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "RechargeInfo")
@TableName("recharge_info")
public class RechargeInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户ID")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "充值订单编号")
	@TableField("order_no")
	private String orderNo;

	@Schema(description = "充值状态：0901-未支付 0902-已支付 0903-已取消")
	@TableField("recharge_status")
	private String rechargeStatus;

	@Schema(description = "充值金额")
	@TableField("recharge_amount")
	private BigDecimal rechargeAmount;

	@Schema(description = "支付方式：1101-微信 1102-支付宝")
	@TableField("pay_way")
	private String payWay;

}