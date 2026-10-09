package com.atguigu.tingshu.search.api;

import com.atguigu.tingshu.common.result.Result;
import com.atguigu.tingshu.search.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "专辑详情管理")
@RestController
@RequestMapping("api/search")
@SuppressWarnings({"all"})
public class itemApiController {

	@Autowired
	private ItemService itemService;


	/**
	 * 查询专辑详情-汇总详情页渲染所需参数
	 * @param albumId
	 * @return {announcer:主播信息,albumInfo:专辑对象,albumStatVo:统计对象, baseCategoryView:分类对象}
	 */
	@Operation(summary = "查询专辑详情-汇总详情页渲染所需参数")
	@GetMapping("/albumInfo/{albumId}")
	public Result<Map<String, Object>> item(@PathVariable Long albumId){
		Map<String, Object> map = itemService.item(albumId);
		return Result.ok(map);
	}
}

