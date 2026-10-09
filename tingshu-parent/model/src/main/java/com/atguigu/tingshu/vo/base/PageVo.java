package com.atguigu.tingshu.vo.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果包装
 *
 * @author atguigu
 */
@Data
@Schema(description = "分页数据消息体")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PageVo<T> implements Serializable {

    @Schema(description = "总条目数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    @Schema(description = "页尺寸", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long size;

    @Schema(description = "总页数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pages;

    @Schema(description = "当前页码", requiredMode = Schema.RequiredMode.REQUIRED)
    private long current;

    @Schema(description = "数据列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<T> records;

    public PageVo(IPage pageModel) {
        this.setRecords(pageModel.getRecords());
        this.setTotal(pageModel.getTotal());
        this.setSize(pageModel.getSize());
        this.setPages(pageModel.getPages());
        this.setCurrent(pageModel.getCurrent());
    }

    public PageVo(List<T> list, IPage pageModel) {
        this.setRecords(list);
        this.setTotal(pageModel.getTotal());
        this.setSize(pageModel.getSize());
        this.setPages(pageModel.getPages());
        this.setCurrent(pageModel.getCurrent());
    }

}
