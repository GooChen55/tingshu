package com.atguigu.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 实体类跟MOngoDB集合对应
 * @author: atguigu
 * @create: 2024-05-17 11:40
 */
@Data
@Document(collection = "user")
public class UserInfo {

    @Id
    private String id;

    private String name;

    private Integer age;

    private String address;
}
