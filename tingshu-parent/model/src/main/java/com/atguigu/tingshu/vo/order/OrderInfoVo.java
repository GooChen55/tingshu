package com.atguigu.tingshu.vo.order;

import com.atguigu.tingshu.common.util.Decimal2Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "订单对象")
public class OrderInfoVo {

    @NotEmpty(message = "交易号不能为空")
    @Schema(description = "交易号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tradeNo;

    @NotEmpty(message = "支付方式不能为空")
    @Schema(description = "支付方式：1101-微信 1102-支付宝 1103-账户余额", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payWay;

    @NotEmpty(message = "付款项目类型不能为空")
    @Schema(description = "付款项目类型: 1001-专辑 1002-声音 1003-vip会员", requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemType;

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
    @DecimalMin(value = "0.00", inclusive = false, message = "订单原始金额必须大于0.00")
    @DecimalMax(value = "9999.99", inclusive = true, message = "订单原始金额必须大于9999.99")
    @Digits(integer = 4, fraction = 2)
    @Schema(description = "订单原始金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal originalAmount;

    @DecimalMin(value = "0.00", inclusive = true, message = "减免总金额必须大于0.00")
    @DecimalMax(value = "9999.99", inclusive = true, message = "减免总金额必须大于9999.99")
    @Digits(integer = 4, fraction = 2)
    @Schema(description = "减免总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal derateAmount;

    @DecimalMin(value = "0.00", inclusive = false, message = "订单总金额必须大于0.00")
    @DecimalMax(value = "9999.99", inclusive = true, message = "订单总金额必须大于9999.99")
    @Digits(integer = 4, fraction = 2)
    @Schema(description = "订单总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal orderAmount;

    @Valid
    @NotEmpty(message = "订单明细列表不能为空")
    @Schema(description = "订单明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderDetailVo> orderDetailVoList;

    @Schema(description = "订单减免明细列表")
    private List<OrderDerateVo> orderDerateVoList;

    @Schema(description = "时间戳", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long timestamp;
    @Schema(description = "签名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sign;
}
