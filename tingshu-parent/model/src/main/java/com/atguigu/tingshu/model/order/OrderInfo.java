package com.atguigu.tingshu.model.order;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "OrderInfo")
@TableName("order_info")
public class OrderInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户ID")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "订单标题")
	@TableField("order_title")
	private String orderTitle;

	@Schema(description = "订单号")
	@TableField("order_no")
	private String orderNo;

	@Schema(description = "订单状态：0901-未支付 0902-已支付 0903-已取消")
	@TableField("order_status")
	private String orderStatus;

	@Schema(description = "订单原始金额")
	@TableField("original_amount")
	private BigDecimal originalAmount;

	@Schema(description = "减免总金额")
	@TableField("derate_amount")
	private BigDecimal derateAmount;

	@Schema(description = "订单总价")
	@TableField("order_amount")
	private BigDecimal orderAmount;

	@Schema(description = "付款项目类型: 1001-专辑 1002-声音 1003-vip会员")
	@TableField("item_type")
	private String itemType;

	@Schema(description = "支付方式：1101-微信 1102-支付宝 1103-账户余额")
	@TableField("pay_way")
	private String payWay;


	@Schema(description = "订单明细列表")
	@TableField(exist = false)
	private List<OrderDetail> orderDetailList;

	@Schema(description = "订单减免明细列表")
	@TableField(exist = false)
	private List<OrderDerate> orderDerateList;

	@TableField(exist = false)
	private String orderStatusName;
	@TableField(exist = false)
	private String payWayName;

}