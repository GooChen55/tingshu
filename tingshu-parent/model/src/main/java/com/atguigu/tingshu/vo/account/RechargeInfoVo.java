package com.atguigu.tingshu.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "充值对象")
public class RechargeInfoVo {

	@Schema(description = "充值金额")
	private BigDecimal amount;

	@Schema(description = "支付方式：1101-微信 1102-支付宝")
	private String payWay;

}