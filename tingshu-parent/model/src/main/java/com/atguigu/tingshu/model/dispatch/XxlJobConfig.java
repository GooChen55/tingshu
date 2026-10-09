package com.atguigu.tingshu.model.dispatch;

import com.atguigu.tingshu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "XxlJobConfig")
@TableName("xxl_job_config")
public class XxlJobConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "调度任务标题")
	@TableField("title")
	private String title;

	@Schema(description = "调度执行handler")
	@TableField("executor_handler")
	private String executorHandler;

	@Schema(description = "调度任务参数")
	@TableField("executor_param")
	private String executorParam;

	@Schema(description = "调度表达式")
	@TableField("cron")
	private String cron;

	@Schema(description = "备注")
	@TableField("remark")
	private String remark;

	@Schema(description = "任务状态    0：失败    1：成功")
	@TableField("status")
	private Integer status;

	@Schema(description = "xxl任务平台id")
	@TableField("xxl_job_id")
	private Long xxlJobId;

}