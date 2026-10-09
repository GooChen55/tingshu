package com.atguigu.tingshu.album.api;

import com.atguigu.tingshu.model.user.UserInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: atguigu
 * @create: 2026-06-03 15:27
 */
public class TestController {


    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3");
        // forEach中参数是Consumer函数式接口类型 只有入参没有出参
        list.stream()
                //.forEach(str -> {
                //    System.out.println("str:" + str);
                //});
                .forEach(System.out::println);
        //map 映射 参数是Function函数式接口 既有入参又有出参
        //需求：将集合中string 转为 UserInfo
        List<UserInfo> newList = list.stream().map(str -> {
            UserInfo userInfo = new UserInfo();
            userInfo.setNickname("昵称：" + str);
            return userInfo;
        }).collect(Collectors.toList());
        System.out.println(newList);


        List<Integer> numList = Arrays.asList(1, 3, 678, 899, 899);
        List<Integer> filterList = numList.stream().filter(num -> {
            //true:需要保留数据
            return num > 3;
        }).collect(Collectors.toList());
        System.out.println(filterList);


        filterList = filterList.stream().distinct().collect(Collectors.toList());
        System.out.println(filterList);


    }


}
