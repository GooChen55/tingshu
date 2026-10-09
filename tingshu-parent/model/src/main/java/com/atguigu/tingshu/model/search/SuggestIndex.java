package com.atguigu.tingshu.model.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

@Data
@Document(indexName = "suggestinfo")
@JsonIgnoreProperties(ignoreUnknown = true)//目的：防止json字符串转成实体对象时因未识别字段报错
public class SuggestIndex {

    /**
     * 提示词文档对象ID 跟专辑ID一致
     */
    @Id
    private String id;

    //存放原始内容，匹配到前缀后给下拉框展示内容 例如：经典留声机
    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;

    //用于汉字自动补全字段   例如：["经典留声机"]
    @CompletionField(analyzer = "standard", searchAnalyzer = "standard", maxInputLength = 20)
    private Completion keyword;

    //用于拼音全拼自动补全字段  例如：["jingdianliushengji"]
    @CompletionField(analyzer = "standard", searchAnalyzer = "standard", maxInputLength = 20)
    private Completion keywordPinyin;

    //用于拼音首字母自动补全字段 例如：["jdlsj"]
    @CompletionField(analyzer = "standard", searchAnalyzer = "standard", maxInputLength = 20)
    private Completion keywordSequence;

}
