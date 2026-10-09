package com.atguigu.tingshu.vo.search;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AlbumSearchResponseVo implements Serializable {


    //检索出来的商品信息
    private List<AlbumInfoIndexVo> list = new ArrayList<>();

    private Long total;//总记录数
    private Integer pageSize;//每页显示的内容
    private Integer pageNo;//当前页面
    private Long totalPages;

}
