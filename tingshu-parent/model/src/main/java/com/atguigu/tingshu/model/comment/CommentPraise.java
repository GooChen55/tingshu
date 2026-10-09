package com.atguigu.tingshu.model.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>
 * CommentPraise
 * </p>
 *
 * @author atguigu
 */
@Data
@Schema(description = "专辑评论点赞")
@Document
public class CommentPraise {

	@Schema(description = "id")
	@Id
	private String id;
	
	@Schema(description = "动态id")
	private Long albumId;

	@Schema(description = "评论id")
	private String commentId;

	@Schema(description = "点赞者id")
	private Long userId;

	@Schema(description = "创建时间")
	private Date createTime;

}

