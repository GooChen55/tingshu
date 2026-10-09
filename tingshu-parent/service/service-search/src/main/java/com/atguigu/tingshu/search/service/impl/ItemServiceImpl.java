package com.atguigu.tingshu.search.service.impl;

import cn.hutool.core.lang.Assert;
import com.atguigu.tingshu.album.AlbumFeignClient;
import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.model.album.BaseCategoryView;
import com.atguigu.tingshu.search.service.ItemService;
import com.atguigu.tingshu.user.client.UserFeignClient;
import com.atguigu.tingshu.vo.album.AlbumStatVo;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@SuppressWarnings({"all"})
public class ItemServiceImpl implements ItemService {

    @Autowired
    private AlbumFeignClient albumFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private Executor threadPoolExecutor;


    /**
     * 查询专辑详情-汇总详情页渲染所需参数
     *
     * @param albumId
     * @return {announcer:主播信息,albumInfo:专辑对象,albumStatVo:统计对象, baseCategoryView:分类对象}
     */
    @Override
    public Map<String, Object> item(Long albumId) {
        //1.初始化Map集合 HashMap是线程不安全 ConcurrentHashMap:线程安全集合
        Map<String, Object> map = new ConcurrentHashMap<>();

        //2.远程调用“专辑服务“获取专辑信息
        CompletableFuture<AlbumInfo> albumInfoCompletableFuture = CompletableFuture.supplyAsync(() -> {
            AlbumInfo albumInfo = albumFeignClient.getAlbumInfo(albumId).getData();
            Assert.notNull(albumInfo, "专辑{}不存在", albumId);
            log.info("获取专辑，线程:{}", Thread.currentThread());
            map.put("albumInfo", albumInfo);
            return albumInfo;
        }, threadPoolExecutor);

        //3.远程调用“专辑服务“获取统计信息
        CompletableFuture<Void> statCompletableFuture = CompletableFuture.runAsync(() -> {
            AlbumStatVo albumStatVo = albumFeignClient.getAlbumStatVo(albumId).getData();
            Assert.notNull(albumStatVo, "专辑{}统计不存在", albumId);
            log.info("获取统计，线程:{}", Thread.currentThread());
            map.put("albumStatVo", albumStatVo);
        }, threadPoolExecutor);

        //4.远程调用“专辑服务“获取分类信息
        CompletableFuture<Void> categoryCompletableFuture = albumInfoCompletableFuture.thenAcceptAsync(albumInfo -> {
            BaseCategoryView baseCategoryView = albumFeignClient.getCategoryView(albumInfo.getCategory3Id()).getData();
            Assert.notNull(baseCategoryView, "专辑{}分类不存在", albumId);
            map.put("baseCategoryView", baseCategoryView);
        }, threadPoolExecutor);

        //5.远程调用“用户服务“获取主播信息
        CompletableFuture<Void> userCompletableFuture = albumInfoCompletableFuture.thenAcceptAsync(albumInfo -> {
            UserInfoVo userInfoVo = userFeignClient.getUserInfoVo(albumInfo.getUserId()).getData();
            Assert.notNull(userInfoVo, "用户{}不存在", albumInfo.getUserId());
            map.put("announcer", userInfoVo);
        }, threadPoolExecutor);

        //6.组合所有异步任务，都必须执行完成
        CompletableFuture.allOf(
                albumInfoCompletableFuture,
                statCompletableFuture,
                categoryCompletableFuture,
                userCompletableFuture
        ).orTimeout(100, TimeUnit.SECONDS).join();

        //7.返回Map集合
        return map;
    }
}
