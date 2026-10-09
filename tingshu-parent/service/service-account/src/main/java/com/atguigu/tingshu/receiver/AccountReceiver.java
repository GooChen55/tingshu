package com.atguigu.tingshu.receiver;

import cn.hutool.core.collection.CollUtil;
import com.atguigu.tingshu.account.service.UserAccountService;
import com.atguigu.tingshu.common.rabbit.constant.MqConst;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: atguigu
 * @create: 2026-06-08 14:27
 */
@Slf4j
@Component
public class AccountReceiver {

    @Autowired
    private UserAccountService userAccountService;

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_USER_REGISTER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_USER, durable = "true"),
            key = MqConst.ROUTING_USER_REGISTER
    ))
    public void initUserAccount(Map<String, Object> map, Message message, Channel channel) {
        if (CollUtil.isNotEmpty(map)) {
            log.info("[账户服务]监听到初始化账户消息：{}", map);
            userAccountService.initUserAccount(map);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
