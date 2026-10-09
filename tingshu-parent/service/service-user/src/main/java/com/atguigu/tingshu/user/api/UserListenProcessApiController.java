package com.atguigu.tingshu.user.api;

import com.atguigu.tingshu.common.login.GuiGuLogin;
import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.common.util.AuthContextHolder;
import com.atguigu.tingshu.user.service.UserListenProcessService;
import com.atguigu.tingshu.vo.user.UserListenProcessVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "用户声音播放进度管理接口")
@RestController
@RequestMapping("api/user")
@SuppressWarnings({"all"})
public class UserListenProcessApiController {

    @Autowired
    private UserListenProcessService userListenProcessService;


    /**
     * 查询当前用户某个声音播放进度
     *
     * @param trackId 声音ID
     * @return 秒
     */
    @GuiGuLogin(required = false)
    @Operation(summary = "查询当前用户某个声音播放进度")
    @GetMapping("/userListenProcess/getTrackBreakSecond/{trackId}")
    public Result<BigDecimal> getTrackBreakSecond(@PathVariable Long trackId) {
        //1.获取当前用户ID
        Long userId = AuthContextHolder.getUserId();
        //2.如果用户ID有值，则查询用户播放进度
        if (userId != null) {
            BigDecimal breakSecond = userListenProcessService.getTrackBreakSecond(userId, trackId);
            return Result.ok(breakSecond);
        }
        return Result.ok(BigDecimal.ZERO);
    }


    /**
     * 更新用户某个声音播放进度
     *
     * @param userListenProcessVo
     * @return
     */
    @GuiGuLogin(required = false)
    @PostMapping("/userListenProcess/updateListenProcess")
    public Result updateListenProcess(@RequestBody UserListenProcessVo userListenProcessVo) {
        //1.获取用户ID
        Long userId = AuthContextHolder.getUserId();
        if (userId != null) {
            //4.调用service方法
            userListenProcessService.updateListenProcess(userId, userListenProcessVo);
        }
        return Result.ok();
    }
}

