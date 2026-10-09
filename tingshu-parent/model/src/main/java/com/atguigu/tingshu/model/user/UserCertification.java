package com.atguigu.tingshu.model.user;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UserCertification")
@TableName("user_certification")
public class UserCertification extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "用户id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "身份证地址1")
	@TableField("id_card1_url")
	private String idCard1Url;

	@Schema(description = "身份证地址2")
	@TableField("id_card2_url")
	private String idCard2Url;

	@Schema(description = "人脸图片地址")
	@TableField("face_url")
	private String faceUrl;

	@Schema(description = "比对结果数据")
	@TableField("result_data")
	private String resultData;

	@Schema(description = "日志操作用户")
	@TableField("operate_user_id")
	private Long operateUserId;

}