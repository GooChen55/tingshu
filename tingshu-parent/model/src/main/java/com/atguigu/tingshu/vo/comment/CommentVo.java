package com.atguigu.tingshu.vo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "专辑评论")
public class CommentVo {

	@Positive(message = "专辑id不能为空")
	@Schema(description = "专辑id")
	private Long albumId;

	@Positive(message = "评分不能为空")
	@Schema(description = "评论中对专辑的评分 （十分制，建议采用五星制，如10分显示五颗星，7分显示三颗半星）")
	private Integer albumCommentScore;

	@NotEmpty(message = "评论内容不能为空")
	@Schema(description = "评论内容")
	private String content;

	@NotEmpty(message = "被回复的评论id不能为空")
	@Schema(description = "被回复的评论id，一级评论默认为：0")
	private String replyCommentId;

}