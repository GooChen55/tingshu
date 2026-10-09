package com.atguigu.tingshu.model.order;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "OrderDerate")
@TableName("order_derate")
public class OrderDerate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "订单id")
	@TableField("order_id")
	private Long orderId;

	@Schema(description = "订单减免类型 1405-专辑折扣 1406-VIP服务折")
	@TableField("derate_type")
	private String derateType;

	@Schema(description = "减免金额")
	@TableField("derate_amount")
	private BigDecimal derateAmount;

	@Schema(description = "备注")
	@TableField("remarks")
	private String remarks;

}