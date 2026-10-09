package com.atguigu.tingshu.search.receiver;

import com.atguigu.tingshu.common.rabbit.constant.MqConst;
import com.atguigu.tingshu.search.service.SearchService;
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

/**
 * @author: atguigu
 * @create: 2026-06-10 14:27
 */
@Slf4j
@Component
public class SearchReceiver {

    @Autowired
    private SearchService searchService;


    /**
     * 上架专辑监听器
     * @param albumId
     * @param channel
     * @param message
     */
    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_ALBUM_UPPER),
            exchange = @Exchange(value = MqConst.EXCHANGE_ALBUM),
            key = {MqConst.ROUTING_ALBUM_UPPER}
    ))
    public void upperAlbum(Long albumId, Channel channel, Message message) {
        if (albumId != null) {
            log.info("上架专辑：{}", albumId);
            searchService.saveAlbumInfoIndex(albumId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 下架专辑监听器
     * @param albumId
     * @param channel
     * @param message
     */
    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_ALBUM_LOWER),
            exchange = @Exchange(value = MqConst.EXCHANGE_ALBUM),
            key = {MqConst.ROUTING_ALBUM_LOWER}
    ))
    public void lowerAlbum(Long albumId, Channel channel, Message message) {
        if (albumId != null) {
            log.info("下架专辑：{}", albumId);
            searchService.removeAlbumInfoIndex(albumId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
