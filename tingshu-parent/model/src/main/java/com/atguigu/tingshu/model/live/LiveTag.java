package com.atguigu.tingshu.model.live;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "LiveTag")
@TableName("live_tag")
public class LiveTag extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "标签名称")
	@TableField("name")
	private String name;

	@Schema(description = "标签图标url")
	@TableField("icon_url")
	private String iconUrl;

	@Schema(description = "排序")
	@TableField("order_num")
	private Integer orderNum;

}