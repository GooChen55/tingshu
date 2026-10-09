package com.atguigu.tingshu.query.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "订单信息")
public class OrderInfoQuery {

	@Schema(description = "创建时间")
	private String createTimeBegin;

	@Schema(description = "创建时间")
	private String createTimeEnd;
}