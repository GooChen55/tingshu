package com.atguigu.tingshu.model.user;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UserPaidAlbum")
@TableName("user_paid_album")
public class UserPaidAlbum extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "订单号")
	@TableField("order_no")
	private String orderNo;

	@Schema(description = "用户ID")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "专辑id")
	@TableField("album_id")
	private Long albumId;

}