package com.atguigu.tingshu.order.client;

import com.atguigu.tingshu.order.client.impl.OrderDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 * 订单模块远程调用API接口
 * </p>
 *
 * @author atguigu
 */
@FeignClient(value = "service-order", fallback = OrderDegradeFeignClient.class)
public interface OrderFeignClient {


}
