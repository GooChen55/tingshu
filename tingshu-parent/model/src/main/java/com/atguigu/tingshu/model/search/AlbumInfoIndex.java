package com.atguigu.tingshu.model.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 数据库字段来源： 业务字段、过滤条件字段、排序字段
 * 1.专辑信息包含标签
 * 2.分类ID（1,2,3级）
 * 3.主播名称
 * 4.统计信息：播放量、订阅量、购买量、评论数
 */
@Data
@Document(indexName = "albuminfo")
@JsonIgnoreProperties(ignoreUnknown = true)//目的：防止json字符串转成实体对象时因未识别字段报错
public class AlbumInfoIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    // 专辑Id
    @Id
    private Long id;

    //  es 中能分词的字段，这个字段数据类型必须是 text！keyword 不分词！ analyzer = "ik_max_word"
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String albumTitle;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String albumIntro;

    @Field(type = FieldType.Keyword)
    private String announcerName;

    //专辑封面
    @Field(type = FieldType.Keyword, index = false)
    private String coverUrl;

    //专辑包含声音总数
    @Field(type = FieldType.Long, index = false)
    private Integer includeTrackCount;

    //专辑是否完结：0-否；1-完结
    @Field(type = FieldType.Long, index = false)
    private String isFinished;

    //付费类型：免费、vip免费、付费
    @Field(type = FieldType.Keyword, index = false)
    private String payType;

    @Field(type = FieldType.Date,format = DateFormat.date_time, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //

    @Field(type = FieldType.Long)
    private Long category1Id;

    @Field(type = FieldType.Long)
    private Long category2Id;

    @Field(type = FieldType.Long)
    private Long category3Id;

    //播放量
    @Field(type = FieldType.Integer)
    private Integer playStatNum = 0;

    //订阅量
    @Field(type = FieldType.Integer)
    private Integer subscribeStatNum = 0;

    //购买量
    @Field(type = FieldType.Integer)
    private Integer buyStatNum = 0;

    //评论数
    @Field(type = FieldType.Integer)
    private Integer commentStatNum = 0;

    //  商品的热度！
    @Field(type = FieldType.Double)
    private Double hotScore = 0d;

    //专辑标签
    // Nested 支持嵌套查询
    @Field(type = FieldType.Nested)
    private List<AttributeValueIndex> attributeValueIndexList;

}
