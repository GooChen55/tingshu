package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "AlbumInfo")
@TableName("album_info")
//springboot默认底层使用JSON框架:jackson
@JsonInclude(JsonInclude.Include.NON_NULL)  //不为空属性会转JSON
public class AlbumInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "标题")
	@TableField("album_title")
	private String albumTitle;

	@Schema(description = "三级分类id")
	@TableField("category3_id")
	private Long category3Id;

	@Schema(description = "专辑简介")
	@TableField("album_intro")
	private String albumIntro;

	@Schema(description = "专辑封面原图，尺寸不固定，最大尺寸为960*960（像素）")
	@TableField("cover_url")
	private String coverUrl;

	@Schema(description = "专辑包含声音总数")
	@TableField("include_track_count")
	private Integer includeTrackCount;

	@Schema(description = "专辑是否完结：0-否；1-完结；")
	@TableField("is_finished")
	private String isFinished;

	@Schema(description = "预计更新多少集")
	@TableField("estimated_track_count")
	private Integer estimatedTrackCount;

	@Schema(description = "专辑简介，富文本")
	@TableField("album_rich_intro")
	private String albumRichIntro;

	@Schema(description = "专辑评分")
	@TableField("quality_score")
	private String qualityScore;

	@Schema(description = "付费类型: 0101-免费、0102-vip免费、0103-付费")
	@TableField("pay_type")
	private String payType;

	@Schema(description = "价格类型： 0201-单集 0202-整专辑")
	@TableField("price_type")
	private String priceType;

	@Schema(description = "原价")
	@TableField("price")
	private BigDecimal price;

	@Schema(description = "0.1-9.9  不打折 -1")
	@TableField("discount")
	private BigDecimal discount;

	@Schema(description = "0.1-9.9 不打折 -1")
	@TableField("vip_discount")
	private BigDecimal vipDiscount;

	@Schema(description = "免费试听集数")
	@TableField("tracks_for_free")
	private Integer tracksForFree;

	@Schema(description = "每集免费试听秒数")
	@TableField("seconds_for_free")
	private Integer secondsForFree;

	@Schema(description = "购买须知，富文本")
	@TableField("buy_notes")
	private String buyNotes;

	@Schema(description = "专辑卖点，富文本")
	@TableField("selling_point")
	private String sellingPoint;

	@Schema(description = "是否公开：0-否 1-是")
	@TableField("is_open")
	private String isOpen;

	@Schema(description = "状态")
	@TableField("status")
	private String status;

	@Schema(description = "属性值集合")
	@TableField(exist = false)
	private List<AlbumAttributeValue> albumAttributeValueVoList;
}
