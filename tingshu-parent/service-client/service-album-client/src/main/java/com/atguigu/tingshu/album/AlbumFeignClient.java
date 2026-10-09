package com.atguigu.tingshu.album;

import com.atguigu.tingshu.album.impl.AlbumDegradeFeignClient;
import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.model.album.BaseCategory3;
import com.atguigu.tingshu.model.album.BaseCategoryView;
import com.atguigu.tingshu.vo.album.AlbumStatVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 专辑模块远程调用Feign接口 目的：简化远程调用  底层产生代理对象仍然发起完整Http请求
 * 1.获取Feign接口上地址，得到 http://service-album/api/album
 * 2.通过Feign方法上得到请求方式、请求路径、请求参数、返回结果 得到：http://service-album/api/album/albumInfo/getAlbumInfo/{id}
 * 3.如何将URL中服务名称改成具体下游服务实例IP:PORT 解决办法：通过Nacos获取目标服务所有实例信息
 * 4.OpenFeign内部集成负载均衡器组件LoadBalancer,默认采用轮询负载均衡策略RoundRobinLoadBalancer 实际请求地址
 *     http://192.168.49.1:8501/api/album/albumInfo/getAlbumInfo/{id}
 *     http://192.168.36.39:8401/api/album/albumInfo/getAlbumInfo/{id}
 * </p>
 *
 * @author atguigu
 */
@FeignClient(value = "service-album", path = "api/album",fallback = AlbumDegradeFeignClient.class)
public interface AlbumFeignClient {


    @GetMapping("/albumInfo/getAlbumInfo/{id}")
    public Result<AlbumInfo> getAlbumInfo(@PathVariable Long id);

    /**
     * 根据3级分类ID查询分类视图
     * @param category3Id
     * @return
     */
    @GetMapping("/category/getCategoryView/{category3Id}")
    public Result<BaseCategoryView> getCategoryView(@PathVariable Long category3Id);


    /**
     * 根据1级分类ID查询置顶7个三级分类列表
     *
     * @return
     */
    @GetMapping("/category/findTopBaseCategory3/{category1Id}")
    public Result<List<BaseCategory3>> findTop7BaseCategory3(@PathVariable Long category1Id);

    /**
     * 根据专辑ID查询统计信息
     * @param albumId
     * @return
     */
    @GetMapping("/albumInfo/getAlbumStatVo/{albumId}")
    public Result<AlbumStatVo> getAlbumStatVo(@PathVariable Long albumId);
}
