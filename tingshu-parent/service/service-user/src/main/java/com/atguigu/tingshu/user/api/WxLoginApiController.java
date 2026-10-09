package com.atguigu.tingshu.user.api;

import com.atguigu.tingshu.common.login.GuiGuLogin;
import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.common.util.AuthContextHolder;
import com.atguigu.tingshu.user.service.UserInfoService;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "微信授权登录接口")
@RestController
@RequestMapping("/api/user/wxLogin")
@Slf4j
public class WxLoginApiController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 微信一键登录
     *
     * @param code 小程序端对接微信获取临时登录凭证code 5分钟只能使用一次
     * @return {token:"访问令牌"}
     */
    @Operation(summary = "小程序微信一键登录")
    @GetMapping("/wxLogin/{code}")
    public Result<Map<String, String>> wxLogin(@PathVariable String code) {
        Map<String, String> map = userInfoService.wxLogin(code);
        return Result.ok(map);
    }

    @GuiGuLogin
    @Operation(summary = "获取当前用户基本信息")
    @GetMapping("/getUserInfo")
    public Result<UserInfoVo> getUserInfoVo() {
        //1.获取当前用户ID
        Long userId = AuthContextHolder.getUserId();
        // 2.调用业务逻辑
        UserInfoVo userInfoVo = userInfoService.getUserInfoVo(userId);
        // 3.返回结果
        return Result.ok(userInfoVo);
    }

    /**
     * 更新当前用户基本信息
     * @param userInfoVo
     * @return
     */
    @GuiGuLogin
    @Operation(summary = "更新当前用户基本信息")
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody UserInfoVo userInfoVo) {
        //1.获取当前用户ID
        Long userId = AuthContextHolder.getUserId();
        //2.调用业务逻辑
        userInfoService.updateUser(userId, userInfoVo);
        //3.返回结果
        return Result.ok();
    }

}
