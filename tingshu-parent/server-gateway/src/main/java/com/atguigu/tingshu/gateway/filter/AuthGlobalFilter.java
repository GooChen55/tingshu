package com.atguigu.tingshu.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 * 全局Filter，统一处理会员登录与外部不允许访问的服务
 * </p>
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {


    /**
     * 方案一：在网关过滤器 拦截请求，实现验证
     * 方案二：请求路由到微服务实例后，通过拦截器拦截，实现验证
     * 方案三：在微服务实例中，在需要校验认证状态接口上新增认证状态校验注解
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        //2.获取认证头信息 业界约定俗成授权头：Authorization
        //request.getHeaders().get("Authorization")
        List<String> tokenList = request.getHeaders().get("token");
        if (CollUtil.isNotEmpty(tokenList)) {
            String token = tokenList.get(0);
            log.info("请求头token:{}", token);
        }
        //3.继续执行
        return chain.filter(exchange);
    }

    /**
     * 优先级：值越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
