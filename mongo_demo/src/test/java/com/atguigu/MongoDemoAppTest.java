package com.atguigu;

import com.atguigu.model.UserInfo;
import com.atguigu.repository.UserRepository;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoDemoAppTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient mongoClient;


    @Test
    public void testRepository() {
        //操作哪个 数据库、哪个集合中文档 数据库名称在yaml文件定义、集合名称在实体类中注解声明
        //UserInfo userInfo = new UserInfo();
        //userInfo.setName("李四");
        //userInfo.setAge(25);
        //userInfo.setAddress("北京朝阳");
        //userRepository.save(userInfo);


        //Optional<UserInfo> optional = userRepository.findById("1");
        //if(optional.isPresent()){
        //    System.out.println(optional.get());
        //}

        //List<UserInfo> list = userRepository.findAll();
        //System.out.println(list);

        //List<UserInfo> list = userRepository.findByName("张三");
        //List<UserInfo> list = userRepository.findByAgeBetween(10, 30);
        List<UserInfo> list = userRepository.findTop1ByAgeBetween(10, 30);
        System.out.println(list);
    }


    @Test
    public void testMongoTemplate() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("jack");
        userInfo.setAge(25);
        userInfo.setAddress("美国洛杉矶");
        //mongoTemplate.save(userInfo);
        mongoTemplate.save(userInfo, "my_user");
    }

}
