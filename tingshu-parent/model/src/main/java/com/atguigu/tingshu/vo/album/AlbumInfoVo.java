package com.atguigu.tingshu.vo.album;

import com.atguigu.tingshu.common.util.Decimal2Serializer;
import com.atguigu.tingshu.validation.NotEmptyPaid;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "专辑信息")
public class AlbumInfoVo {

	@NotEmpty(message = "专辑标题不能为空")
	@Length(min = 2, message = "专辑标题的长度必须大于2")
	@Schema(description = "标题", required=true)
	private String albumTitle;

	@Positive(message = "三级分类不能为空")
	@Schema(description = "三级分类id", required=true)
	private Long category3Id;

	@NotEmpty(message = "专辑简介不能为空")
	@Schema(description = "专辑简介", required=true)
	private String albumIntro;

	@NotEmpty(message = "专辑封面不能为空")
	@Schema(description = "专辑封面图", required=true)
	private String coverUrl;

	@Schema(description = "预计更新多少集")
	private Integer estimatedTrackCount;

	@Schema(description = "专辑简介，富文本")
	private String albumRichIntro;

	@NotEmpty(message = "付费类型不能为空")
	@Schema(description = "付费类型: 0101-免费、0102-vip免费、0103-付费", required=true)
	private String payType;

	@Schema(description = "价格类型： 0201-单集 0202-整专辑")
	private String priceType;

	@Schema(description = "原价")
	@JsonSerialize(using = Decimal2Serializer.class)
	private BigDecimal price;

	@Schema(description = "0.1-9.9  不打折 -1")
	@JsonSerialize(using = Decimal2Serializer.class)
	private BigDecimal discount = new BigDecimal(-1);

	@Schema(description = "0.1-9.9 不打折 -1")
	@JsonSerialize(using = Decimal2Serializer.class)
	private BigDecimal vipDiscount = new BigDecimal(-1);

	@Schema(description = "免费试听集数")
	private Integer tracksForFree;

	@Schema(description = "每集免费试听秒数")
	private Integer secondsForFree;

	@Schema(description = "购买须知，富文本")
	private String buyNotes;

	@Schema(description = "专辑卖点，富文本")
	private String sellingPoint;

	@Schema(description = "是否公开：0-否 1-是")
	private String isOpen;

	//递归校验
	//@Valid
	//@NotEmpty(message = "属性值集合不能为空")
	@Schema(description = "属性值集合")
	private List<AlbumAttributeValueVo> albumAttributeValueVoList;

	@NotEmptyPaid(message = "价格类型不能为空")
	public String getPayTypeAndPriceType() {
		return this.getPayType() + "_" + this.getPriceType();
	}

	@NotEmptyPaid(message = "价格不能为空")
	public String getPayTypeAndPrice() {
		return this.getPayType() + "_" + this.getPrice();
	}
}