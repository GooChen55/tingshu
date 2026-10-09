package com.atguigu.tingshu.common.constant;

public class SystemConstant {

    //专辑付费类型  0101-免费、0102-vip免费、0103-付费
    public static final String ALBUM_PAY_TYPE_FREE = "0101";  // 免费
    public static final String ALBUM_PAY_TYPE_VIPFREE = "0102";  // vip免费
    public static final String ALBUM_PAY_TYPE_REQUIRE = "0103";  // 付费

    //专辑价格类型 0201-单集 0202-整专辑
    public static final String ALBUM_PRICE_TYPE_ONE = "0201";  // 单级
    public static final String ALBUM_PRICE_TYPE_ALL = "0202";  // 整专辑

    //专辑状态 0301-审核通过 0302-审核不通过
    public static final String ALBUM_STATUS_PASS = "0301";  // 审核通过
    public static final String ALBUM_STATUS_NO_PASS = "0302";  // 审核不通过
    public static final String ALBUM_STATUS_MANUAL = "0303";  // 人工审核

    //专辑统计 0401-播放量 0402-订阅量 0403-购买量 0403-评论数
    public static final String ALBUM_STAT_PLAY = "0401";  // 播放量
    public static final String ALBUM_STAT_SUBSCRIBE = "0402";  // 订阅量
    public static final String ALBUM_STAT_BUY = "0403";  // 购买量
    public static final String ALBUM_STAT_COMMENT = "0404";  // 评论数

    //声音状态 0501-审核通过 0502"-审核不通过
    public static final String TRACK_STATUS_PASS = "0501";  // 审核通过
    public static final String TRACK_STATUS_NO_PASS = "0502";  // 审核不通过
    public static final String TRACK_STATUS_MANUAL = "0503";  // 人工审核
    public static final String TRACK_STATUS_REVIEWING = "0504";  // 音频文件机审中

    //声音来源 0601-用户原创 0602-上传
    public static final String TRACK_SOURCE_USER = "0601";  // 用户原创
    public static final String TRACK_SOURCE_UPLOAD = "0602";  // 上传

    // 声音统计 0701-播放量 0702-收藏量 0703-点赞量 0704-评论数
    public static final String TRACK_STAT_PLAY = "0701";  // 播放量
    public static final String TRACK_STAT_COLLECT = "0702";  // 收藏量
    public static final String TRACK_STAT_PRAISE = "0703";  // 点赞量
    public static final String TRACK_STAT_COMMENT = "0704";  // 评论数

    //用户状态 0801-正常 0802-锁定
    public static final String USER_STATUS_NORMAL = "0801";  // 正常
    public static final String USER_STATUS_LOCK = "0802";  // 锁定

    //订单状态 0901-正常 0902-已支付 0903-已取消
    public static final String ORDER_STATUS_UNPAID = "0901";  // 未支付
    public static final String ORDER_STATUS_PAID = "0902";  // 已支付
    public static final String ORDER_STATUS_CANCEL = "0903";  // 已取消

    //订单付款项目类型 1001-专辑 1002-声音 1003-vip会员
    public static final String ORDER_ITEM_TYPE_ALBUM = "1001";  // 专辑
    public static final String ORDER_ITEM_TYPE_TRACK = "1002";  // 声音
    public static final String ORDER_ITEM_TYPE_VIP = "1003";  // vip会员

    //订单支付方式 1101-微信 1102-支付宝 1103-账户余额
    public static final String ORDER_PAY_WAY_WEIXIN = "1101";  // 微信
    public static final String ORDER_PAY_WAY_ALIPAY = "1102";  // 支付宝
    public static final String ORDER_PAY_ACCOUNT = "1103";  // 账户余额

    //账号交易类型 1201-充值 1202-锁定 1203-解锁 1204-消费
    public static final String ACCOUNT_TRADE_TYPE_DEPOSIT = "1201";  // 充值
    public static final String ACCOUNT_TRADE_TYPE_LOCK = "1202";  // 锁定
    public static final String ACCOUNT_TRADE_TYPE_UNLOCK = "1203";  // 解锁
    public static final String ACCOUNT_TRADE_TYPE_MINUS = "1204";  // 消费

    //支付类型 1301-订单 1302-充值
    public static final String PAYMENT_TYPE_ORDER = "1301";  // 订单
    public static final String PAYMENT_TYPE_RECHARGE = "1302";  // 充值

    //支付表支付状态 1401-未支付 1402-已支付
    public static final String PAYMENT_STATUS_UNPAID = "1401";  // 未支付
    public static final String PAYMENT_STATUS_PAID = "1402";  // 已支付

    //订单减免类型 1405-专辑折扣 1406-VIP服务折
    public static final String ORDER_DERATE_ALBUM_DISCOUNT = "1405";  // 专辑折扣
    public static final String ORDER_DERATE_VIP_SERVICE_DISCOUNT = "1406";  // VIP服务折扣
}
