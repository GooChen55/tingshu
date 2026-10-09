package com.atguigu.tingshu.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "UserListenProcess")
@Document
public class UserListenProcess {

	@Schema(description = "id")
	@Id
	private String id;

	@Schema(description = "用户id")
	private Long userId;

	@Schema(description = "专辑id")
	private Long albumId;

	@Schema(description = "声音id，声音id为0时，浏览的是专辑")
	private Long trackId;

	@Schema(description = "相对于音频开始位置的播放跳出位置，单位为秒。比如当前音频总时长60s，本次播放到音频第25s处就退出或者切到下一首，那么break_second就是25")
	private BigDecimal breakSecond;

	@Schema(description = "是否显示")
	private Integer isShow;

	@Schema(description = "创建时间")
	private Date createTime;

	@Schema(description = "更新时间")
	private Date updateTime;

}