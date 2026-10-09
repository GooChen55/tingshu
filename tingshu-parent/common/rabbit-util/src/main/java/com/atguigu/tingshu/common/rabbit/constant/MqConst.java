package com.atguigu.tingshu.common.rabbit.constant;

public class MqConst {

    /**
     * 专辑
     */
    public static final String EXCHANGE_ALBUM = "tingshu.album";
    public static final String ROUTING_ALBUM_UPPER  = "tingshu.album.upper";
    public static final String ROUTING_ALBUM_LOWER  = "tingshu.album.lower";
    public static final String ROUTING_ALBUM_STAT_UPDATE = "tingshu.album.stat.update";
    public static final String ROUTING_ALBUM_ES_STAT_UPDATE = "tingshu.album.es.stat.update";
    public static final String ROUTING_ALBUM_RANKING_UPDATE = "tingshu.album.ranking.update";
    //队列
    public static final String QUEUE_ALBUM_UPPER  = "tingshu.album.upper";
    public static final String QUEUE_ALBUM_LOWER  = "tingshu.album.lower";
    public static final String QUEUE_ALBUM_STAT_UPDATE = "tingshu.album.stat.update";
    public static final String QUEUE_ALBUM_ES_STAT_UPDATE = "tingshu.album.es.stat.update";
    public static final String QUEUE_ALBUM_RANKING_UPDATE = "tingshu.album.ranking.update";

    /**
     * 声音
     */
    public static final String EXCHANGE_TRACK = "tingshu.track";
    public static final String ROUTING_TRACK_STAT_UPDATE = "tingshu.track.stat.update";
    public static final String QUEUE_TRACK_STAT_UPDATE = "tingshu.track.stat.update";

    /**
     * 取消订单
     */
    //延迟取消订单队列
    /**
     * 取消订单延迟消息
     */
    public static final String EXCHANGE_CANCEL_ORDER = "tingshu.cancel.order";
    public static final String ROUTING_CANCEL_ORDER = "tingshu.cancel.order";
    public static final String QUEUE_CANCEL_ORDER = "tingshu.cancel.order";
    public static final Integer CANCEL_ORDER_DELAY_TIME = 15 * 60;

    /**
     * 支付
     */
    public static final String EXCHANGE_ORDER = "tingshu.order";
    public static final String ROUTING_ORDER_PAY_SUCCESS  = "tingshu.order.pay.success";
    public static final String ROUTING_RECHARGE_PAY_SUCCESS  = "tingshu.recharge.pay.success";
    public static final String QUEUE_ORDER_PAY_SUCCESS  = "tingshu.order.pay.success";
    public static final String QUEUE_RECHARGE_PAY_SUCCESS  = "tingshu.recharge.pay.success";


    /**
     * 账户
     */
    public static final String EXCHANGE_ACCOUNT = "tingshu.account";
    public static final String ROUTING_ACCOUNT_UNLOCK  = "tingshu.account.unlock";
    public static final String ROUTING_ACCOUNT_MINUS  = "tingshu.account.minus";
    public static final String QUEUE_ACCOUNT_UNLOCK  = "tingshu.account.unlock";
    public static final String QUEUE_ACCOUNT_MINUS  = "tingshu.account.minus";

    /**
     * 用户
     */
    public static final String EXCHANGE_USER = "tingshu.user";
    public static final String ROUTING_USER_PAY_RECORD  = "tingshu.user.pay.record";
    public static final String ROUTING_USER_REGISTER  = "tingshu.user.register";
    public static final String ROUTING_USER_VIP_EXPIRE_STATUS = "tingshu.user.vip.expire.status";
    public static final String QUEUE_USER_PAY_RECORD  = "tingshu.user.pay.record";
    public static final String QUEUE_USER_REGISTER  = "tingshu.user.register";
    public static final String QUEUE_USER_VIP_EXPIRE_STATUS = "tingshu.user.vip.expire.status";

    /**
     * 热门关键字
     */
    public static final String EXCHANGE_KEYWORD = "tingshu.keyword";
    public static final String ROUTING_KEYWORD_INPUT  = "tingshu.keyword.input";
    public static final String ROUTING_KEYWORD_OUT  = "tingshu.keyword.out";
    public static final String QUEUE_KEYWORD_INPUT  = "tingshu.keyword.input";
    public static final String QUEUE_KEYWORD_OUT  = "tingshu.keyword.out";


}
