package com.atguigu.tingshu.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "订单确认对象")
public class TradeResponeVo {

    @Schema(description = "订单原始金额")
    private BigDecimal originalAmount;

    @Schema(description = "减免总金额")
    private BigDecimal derateAmount;

    @Schema(description = "订单总价")
    private BigDecimal orderAmount;

    @Schema(description = "付款项目类型: 1001-专辑 1002-声音 1003-vip会员")
    private String itemType;

    @Schema(description = "付款项目类型Id")
    private Long itemId;

    @Schema(description = "针对声音购买，购买当前集往后多少集")
    private Integer trackCount;

    @Schema(description = "交易号")
    private String tradeNo;

    @Schema(description = "付款项目详情")
    List<Object> detailList;

}