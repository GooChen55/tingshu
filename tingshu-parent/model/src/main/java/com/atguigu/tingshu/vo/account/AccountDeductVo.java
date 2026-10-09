package com.atguigu.tingshu.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "扣减金额对象")
public class AccountDeductVo {

	@Schema(description = "订单号")
	private String orderNo;

	@Schema(description = "用户id")
	private Long userId;

	@Schema(description = "扣减金额")
	private BigDecimal amount;

	@Schema(description = "扣减内容")
	private String content;

}
