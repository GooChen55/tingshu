package com.atguigu.tingshu.user.client;

import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.user.client.impl.UserDegradeFeignClient;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户模块远程调用API接口
 * </p>
 *
 * @author atguigu
 */
@FeignClient(value = "service-user",path = "api/user", fallback = UserDegradeFeignClient.class)
public interface UserFeignClient {

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    @GetMapping("/userInfo/getUserInfoVo/{userId}")
    public Result<UserInfoVo> getUserInfoVo(@PathVariable Long userId);

    /**
     * 检查每个提交声音购买状态，如果已购买将购买状态设置为1，反之设置为0
     * @param userId 用户ID
     * @param albumId 专辑ID
     * @param needCheckPayStatusTrackIdList 待检查购买状态声音ID列表
     * @return 每个声音购买状态 {声音ID:购买状态}
     */
    @PostMapping("/userInfo/userIsPaidTrack/{userId}/{albumId}")
    public Result<Map<Long, Integer>> userIsPaidTrack(
            @PathVariable Long userId,
            @PathVariable Long albumId,
            @RequestBody List<Long> needCheckPayStatusTrackIdList
    );
}
