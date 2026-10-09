package com.atguigu.tingshu;

import com.atguigu.tingshu.album.AlbumFeignClient;
import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.search.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceSearchApplicationTest {


    @Autowired
    private AlbumFeignClient albumFeignClient;


    @Test
    public void testFeign() {
        for (int i = 0; i < 10; i++) {
            Result<AlbumInfo> result = albumFeignClient.getAlbumInfo(1L);
            System.out.println(result.getData());
        }
    }


    @Autowired
    private SearchService searchService;

    /**
     * 不严谨批量导入
     */
    @Test
    public void test() {
        for (long i = 1; i <= 1623; i++) {
            try {
                searchService.saveAlbumInfoIndex(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
