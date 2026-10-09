package com.atguigu.tingshu.model.user;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UserPaidTrack")
@TableName("user_paid_track")
public class UserPaidTrack extends BaseEntity {

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

	@Schema(description = "声音id")
	@TableField("track_id")
	private Long trackId;

}