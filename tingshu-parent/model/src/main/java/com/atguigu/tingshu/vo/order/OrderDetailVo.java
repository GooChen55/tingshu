package com.atguigu.tingshu.vo.order;

import com.atguigu.tingshu.common.util.Decimal2Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "OrderDetail")
public class OrderDetailVo {

	@NotNull(message = "付费项目id不能为空")
	@Schema(description = "付费项目id")
	private Long itemId;

	@NotEmpty(message = "付费项目id不能为空")
	@Schema(description = "付费项目名称")
	private String itemName;

	@NotEmpty(message = "付费项目id不能为空")
	@Schema(description = "付费项目图片url")
	private String itemUrl;

	/**
	 * value：最小值
	 * inclusive：是否可以等于最小值，默认true，>= 最小值
	 * message：错误提示（默认有一个错误提示i18n支持中文）
	 *
	 * @DecimalMax 同上
	 * @Digits integer： 整数位最多几位
	 * fraction：小数位最多几位
	 * message：同上，有默认提示
	 */
	@DecimalMin(value = "0.00", inclusive = false, message = "付费项目价格必须大于0.00")
	@DecimalMax(value = "9999.99", inclusive = true, message = "付费项目价格必须大于9999.99")
	@Digits(integer = 4, fraction = 2)
	@Schema(description = "付费项目价格")
	@JsonSerialize(using = Decimal2Serializer.class)
	private BigDecimal itemPrice;

}