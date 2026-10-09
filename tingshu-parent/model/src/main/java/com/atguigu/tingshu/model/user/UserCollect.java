package com.atguigu.tingshu.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Schema(description = "用户收藏")
@Document
public class UserCollect {

	@Schema(description = "id")
	@Id
	private String id;

	@Schema(description = "用户ID")
	private Long userId;

	@Schema(description = "声音ID")
	private Long trackId;

	@Schema(description = "创建时间")
	private Date createTime;

}