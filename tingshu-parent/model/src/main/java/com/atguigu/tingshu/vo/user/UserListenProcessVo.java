package com.atguigu.tingshu.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "UserListenProcess")
public class UserListenProcessVo {

	@Schema(description = "专辑id")
	private Long albumId;

	@Schema(description = "声音id，声音id为0时，浏览的是专辑")
	private Long trackId;

	@Schema(description = "相对于音频开始位置的播放跳出位置，单位为秒。比如当前音频总时长60s，本次播放到音频第25s处就退出或者切到下一首，那么break_second就是25")
	@TableField("break_second")
	private BigDecimal breakSecond;

}