package com.atguigu.tingshu.vo.order;

import com.atguigu.tingshu.common.util.Decimal2Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "订单减免明细")
public class OrderDerateVo {

	@Schema(description = "订单减免类型 1405-专辑折扣 1406-VIP服务折")
	private String derateType;

	@Schema(description = "减免金额")
	@JsonSerialize(using = Decimal2Serializer.class)
	private BigDecimal derateAmount;

	@Schema(description = "备注")
	private String remarks;

}