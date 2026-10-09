package com.atguigu.repository;

import com.atguigu.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserInfo, String> {

    List<UserInfo> findByName(String name);

    List<UserInfo> findByAgeBetween(Integer min, Integer max);
    List<UserInfo> findTop1ByAgeBetween(Integer min, Integer max);

}
