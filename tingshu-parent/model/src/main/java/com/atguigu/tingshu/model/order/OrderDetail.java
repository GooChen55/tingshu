package com.atguigu.tingshu.model.order;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "OrderDetail")
@TableName("order_detail")
public class OrderDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "订单id")
	@TableField("order_id")
	private Long orderId;

	@Schema(description = "付费项目id")
	@TableField("item_id")
	private Long itemId;

	@Schema(description = "付费项目名称")
	@TableField("item_name")
	private String itemName;

	@Schema(description = "付费项目图片url")
	@TableField("item_url")
	private String itemUrl;

	@Schema(description = "付费项目价格")
	@TableField("item_price")
	private BigDecimal itemPrice;

}