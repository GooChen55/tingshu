package com.atguigu.tingshu.vo.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "支付信息")
public class PaymentVo {


	@Schema(description = "支付类型：1301-订单 1302-充值")
	private String paymentType;

	@Schema(description = "订单号")
	private String orderNo;

	@Schema(description = "付款方式：1101-微信 1102-支付宝")
	private String payWay;

	@Schema(description = "支付金额")
	private String amount;

	@Schema(description = "交易内容")
	private String content;

}