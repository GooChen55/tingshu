package com.atguigu.tingshu.user.service;

import com.atguigu.tingshu.model.user.UserInfo;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {

    /**
     * 微信一键登录
     * @param code 小程序端对接微信获取临时登录凭证code 5分钟只能使用一次
     * @return {token:"访问令牌"}
     */
    Map<String, String> wxLogin(String code);

    UserInfoVo getUserInfoVo(Long userId);


    void updateUser(Long userId, UserInfoVo userInfoVo);

    /**
     * 检查每个提交声音购买状态，如果已购买将购买状态设置为1，反之设置为0
     * @param userId 用户ID
     * @param albumId 专辑ID
     * @param needCheckPayStatusTrackIdList 待检查购买状态声音ID列表
     * @return 每个声音购买状态 {声音ID:购买状态}
     */
    Map<Long, Integer> userIsPaidTrack(Long userId, Long albumId, List<Long> needCheckPayStatusTrackIdList);
}
