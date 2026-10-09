package com.atguigu.tingshu.model.user;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "VipServiceConfig")
@TableName("vip_service_config")
public class VipServiceConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "服务名称")
	@TableField("name")
	private String name;

	@Schema(description = "原价，单位元，用于营销展示")
	@TableField("price")
	private BigDecimal price;

	@Schema(description = "折后价，单位元，即实际价格")
	@TableField("discount_price")
	private BigDecimal discountPrice;

	@Schema(description = "优惠简介")
	@TableField("intro")
	private String intro;

	@Schema(description = "服务简介，富文本")
	@TableField("rich_intro")
	private String richIntro;

	@Schema(description = "服务月数")
	@TableField("service_month")
	private Integer serviceMonth;

	@Schema(description = "服务图片url")
	@TableField("image_url")
	private String imageUrl;

}