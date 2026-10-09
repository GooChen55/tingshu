package com.atguigu.tingshu.user.api;

import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.user.service.UserInfoService;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "用户管理接口")
@RestController
@RequestMapping("api/user")
@SuppressWarnings({"all"})
public class UserInfoApiController {

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 根据用户ID查询用户信息
	 * @param userId
	 * @return
	 */
	@Operation(summary = "根据用户ID查询用户信息")
	@GetMapping("/userInfo/getUserInfoVo/{userId}")
	public Result<UserInfoVo> getUserInfoVo(@PathVariable Long userId){
		UserInfoVo userInfoVo = userInfoService.getUserInfoVo(userId);
		return Result.ok(userInfoVo);
	}

	/**
	 * 检查每个提交声音购买状态，如果已购买将购买状态设置为1，反之设置为0
	 * @param userId 用户ID
	 * @param albumId 专辑ID
	 * @param needCheckPayStatusTrackIdList 待检查购买状态声音ID列表
	 * @return 每个声音购买状态 {声音ID:购买状态}
	 */
	@Operation(summary = "检查每个提交声音购买状态，如果已购买将购买状态设置为1，反之设置为0")
	@PostMapping("/userInfo/userIsPaidTrack/{userId}/{albumId}")
	public Result<Map<Long, Integer>> userIsPaidTrack(
			@PathVariable Long userId,
			@PathVariable Long albumId,
			@RequestBody List<Long> needCheckPayStatusTrackIdList
	){
		Map<Long, Integer> map = userInfoService.userIsPaidTrack(userId, albumId, needCheckPayStatusTrackIdList);
		return Result.ok(map);
	}

}

