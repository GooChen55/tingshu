package com.atguigu.tingshu.model.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "专辑评论")
@Document
public class Comment {

	@Schema(description = "id")
	@Id
	private String id;

	@Schema(description = "专辑id")
	private Long albumId;

	@Schema(description = "用户id")
	private Long userId;

	@Schema(description = "发表评论用户的昵称，已脱敏")
	private String nickname;

	@Schema(description = "发表评论用户的头像图片地址")
	private String avatarUrl;

	@Schema(description = "评论内容")
	private String content;

	@Schema(description = "被回复的评论id")
	private String replyCommentId;

	@Schema(description = "点赞数量")
	private Integer praiseCount = 0;

	@Schema(description = "评论中对专辑的评分 （十分制，建议采用五星制，如10分显示五颗星，7分显示三颗半星）")
	private Integer albumCommentScore;

	@Schema(description = "创建时间")
	private Date createTime;

	@Schema(description = "当前用户是否点赞")
	@Transient
	private Boolean isPraise;

	@Schema(description = "回复评论列表")
	@Transient //被该注解标注的，将不会被录入到数据库中。只作为普通的javaBean属性
	private List<Comment> replyCommentList;
}