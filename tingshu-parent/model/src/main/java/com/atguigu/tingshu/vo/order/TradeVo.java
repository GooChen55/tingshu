package com.atguigu.tingshu.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "订单确认对象")
public class TradeVo {

    @NotEmpty(message = "付款项目类型不能为空")
    @Schema(description = "付款项目类型: 1001-专辑 1002-声音 1003-vip会员", requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemType;

    @Positive(message = "付款项目类型Id不能为空")
    @Schema(description = "付款项目类型Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long itemId;

    @Schema(description = "针对声音购买，购买当前集往后多少集", required = false)
    private Integer trackCount;

}
