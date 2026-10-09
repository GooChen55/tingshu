package com.atguigu.tingshu.model.payment;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "PaymentInfo")
@TableName("payment_info")
public class PaymentInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "支付类型：1301-订单 1302-充值")
	@TableField("payment_type")
	private String paymentType;

	@Schema(description = "订单号")
	@TableField("order_no")
	private String orderNo;

	@Schema(description = "付款方式：1101-微信 1102-支付宝")
	@TableField("pay_way")
	private String payWay;

	@Schema(description = "交易编号（微信或支付）")
	@TableField("out_trade_no")
	private String outTradeNo;

	@Schema(description = "支付金额")
	@TableField("amount")
	private BigDecimal amount;

	@Schema(description = "交易内容")
	@TableField("content")
	private String content;

	@Schema(description = "支付状态：1401-未支付 1402-已支付")
	@TableField("payment_status")
	private String paymentStatus;

	@Schema(description = "回调时间")
	@TableField("callback_time")
	private Date callbackTime;

	@Schema(description = "回调信息")
	@TableField("callback_content")
	private String callbackContent;

}