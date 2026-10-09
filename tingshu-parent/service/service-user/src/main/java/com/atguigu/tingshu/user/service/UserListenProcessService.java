package com.atguigu.tingshu.user.service;

import com.atguigu.tingshu.vo.user.UserListenProcessVo;

import java.math.BigDecimal;

public interface UserListenProcessService {

    /**
     * 查询指定用户某个声音播放进度
     *
     * @param trackId 声音ID
     * @return 秒
     */
    BigDecimal getTrackBreakSecond(Long userId, Long trackId);

    /**
     * 更新用户某个声音播放进度
     * @param userId 用户ID
     * @param userListenProcessVo 播放进度VO
     * @return
     */
    void updateListenProcess(Long userId, UserListenProcessVo userListenProcessVo);
}
