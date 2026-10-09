package com.atguigu.tingshu.model.album;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "AlbumStat")
@TableName("album_stat")
public class AlbumStat extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "专辑id")
	@TableField("album_id")
	private Long albumId;

	@Schema(description = "统计类型")
	@TableField("stat_type")
	private String statType;

	@Schema(description = "统计数目")
	@TableField("stat_num")
	private Integer statNum;

}