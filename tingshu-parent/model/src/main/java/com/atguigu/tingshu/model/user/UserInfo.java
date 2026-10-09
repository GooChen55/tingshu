package com.atguigu.tingshu.model.user;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "UserInfo")
@TableName("user_info")
public class UserInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "手机")
	@TableField("phone")
	private String phone;

	@Schema(description = "密码")
	@TableField("password")
	private String password;

	@Schema(description = "微信openId")
	@TableField("wx_open_id")
	private String wxOpenId;

	@Schema(description = "nickname")
	@TableField("nickname")
	private String nickname;

	@Schema(description = "主播用户头像图片")
	@TableField("avatar_url")
	private String avatarUrl;

	@Schema(description = "用户是否为VIP会员 0:普通用户  1:VIP会员")
	@TableField("is_vip")
	private Integer isVip;

	@Schema(description = "当前VIP到期时间，即失效时间")
	@TableField("vip_expire_time")
	private Date vipExpireTime;

	@Schema(description = "性别")
	@TableField("gender")
	private Integer gender;

	@Schema(description = "出生年月")
	@TableField("birthday")
	private Date birthday;

	@Schema(description = "简介")
	@TableField("intro")
	private String intro;

	@Schema(description = "主播认证类型")
	@TableField("certification_type")
	private Integer certificationType;

	@Schema(description = "认证状态")
	@TableField("certification_status")
	private Integer certificationStatus;

	@Schema(description = "状态")
	@TableField("status")
	private String status;

}
