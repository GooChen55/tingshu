package com.atguigu.tingshu.query.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "声音信息")
public class UserInfoQuery {

	@Schema(description = "手机")
	private String phone;

	@Schema(description = "昵称")
	private String nickname;

	@Schema(description = "创建时间")
	private String createTimeBegin;

	@Schema(description = "创建时间")
	private String createTimeEnd;
}