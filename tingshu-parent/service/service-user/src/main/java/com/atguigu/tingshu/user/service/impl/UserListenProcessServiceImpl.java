package com.atguigu.tingshu.user.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.atguigu.tingshu.common.constant.RedisConstant;
import com.atguigu.tingshu.common.constant.SystemConstant;
import com.atguigu.tingshu.common.rabbit.constant.MqConst;
import com.atguigu.tingshu.common.rabbit.service.RabbitService;
import com.atguigu.tingshu.common.util.MongoUtil;
import com.atguigu.tingshu.model.user.UserListenProcess;
import com.atguigu.tingshu.user.service.UserListenProcessService;
import com.atguigu.tingshu.vo.album.TrackStatMqVo;
import com.atguigu.tingshu.vo.user.UserListenProcessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.atguigu.tingshu.common.util.MongoUtil.MongoCollectionEnum.USER_LISTEN_PROCESS;

@Service
@SuppressWarnings({"all"})
public class UserListenProcessServiceImpl implements UserListenProcessService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitService rabbitService;

    private String get_collection_name(MongoUtil.MongoCollectionEnum emnum, Long userId) {
        return MongoUtil.getCollectionName(emnum, userId);
    }

    /**
     * 查询指定用户某个声音播放进度
     * 从MongoDB中查询
     *
     * @param trackId 声音ID
     * @return 秒
     */
    @Override
    public BigDecimal getTrackBreakSecond(Long userId, Long trackId) {
        //1.确定用户集合名称 形式：前缀+用户ID
        String collectionName = this.get_collection_name(USER_LISTEN_PROCESS, userId);
        //2.确定查询条件
        Query query = new Query();
        query.addCriteria(Criteria.where("trackId").is(trackId).and("userId").is(userId));
        //3.执行查询
        UserListenProcess userListenProcess = mongoTemplate.findOne(query, UserListenProcess.class, collectionName);

        //4.返回播放进度
        if (userListenProcess != null) {
            return userListenProcess.getBreakSecond();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 播放过程中，每隔10s触发一次
     * 更新用户某个声音播放进度
     *
     * @param userId              用户ID
     * @param userListenProcessVo 播放进度VO
     * @return
     */
    @Override
    public void updateListenProcess(Long userId, UserListenProcessVo userListenProcessVo) {
        //1.确定用户集合名称 形式：前缀+用户ID
        String collectionName = this.get_collection_name(USER_LISTEN_PROCESS, userId);
        //2.确定查询条件
        Query query = new Query();
        query.addCriteria(Criteria.where("trackId").is(userListenProcessVo.getTrackId()).and("userId").is(userId));
        //3.执行查询
        UserListenProcess userListenProcess = mongoTemplate.findOne(query, UserListenProcess.class, collectionName);
        //4.如果播放进度存在则更新：秒数、更新时间
        BigDecimal breakSecond = userListenProcessVo.getBreakSecond().setScale(0, RoundingMode.HALF_UP);
        if (userListenProcess != null) {
            userListenProcess.setBreakSecond(breakSecond);
            userListenProcess.setUpdateTime(new Date());
        } else {
            //5.如果播放进度不存在则插入
            userListenProcess = new UserListenProcess();
            userListenProcess.setAlbumId(userListenProcessVo.getAlbumId());
            userListenProcess.setBreakSecond(breakSecond);
            userListenProcess.setCreateTime(new Date());
            userListenProcess.setTrackId(userListenProcessVo.getTrackId());
            userListenProcess.setUpdateTime(new Date());
            userListenProcess.setUserId(userId);
        }
        //文档ID存在则更新，不存在则新增
        mongoTemplate.save(userListenProcess, collectionName);

        //6.TODO 采用MQ消息中间件 异步方式增量更新统计数值（MySQL库，ES索引库）
        //6.1 在00:00点前，某个用户对于某个声音播放统计数值累加1次 基于Redis的set k v ex nx 生产消息幂等性
        //6.1.1 计算key过期时间 当日结束时间毫秒-当前时间毫秒
        long ttl = DateUtil.endOfDay(new Date()).getTime() - System.currentTimeMillis();
        //6.1.2 构建幂等性Key
        String key = RedisConstant.USER_TRACK_REPEAT_STAT_PREFIX + userId + "_" + userListenProcessVo.getAlbumId() + "_" + userListenProcess.getTrackId();
        //6.1.3 采用set nx存入Redis
        Boolean flag = redisTemplate.opsForValue()
                .setIfAbsent(key, userListenProcessVo.getTrackId(), ttl, TimeUnit.MILLISECONDS);

        //6.2 存入Redis成功，发送增量更新统计数值MQ消息 通知：专辑服务、搜索服务 更新统计数值
        if(flag){
            //6.2.1 创建增量更新统计数值MQ消息 实体类必须实现序列化接口
            TrackStatMqVo trackStatMqVo = new TrackStatMqVo();
            trackStatMqVo.setBusinessNo("mq:"+ IdUtil.randomUUID());
            trackStatMqVo.setAlbumId(userListenProcessVo.getAlbumId());
            trackStatMqVo.setTrackId(userListenProcessVo.getTrackId());
            trackStatMqVo.setStatType(SystemConstant.TRACK_STAT_PLAY);
            trackStatMqVo.setCount(1);
            //6.2.2 发送MQ消息
            rabbitService.sendMessage(MqConst.EXCHANGE_TRACK, MqConst.ROUTING_TRACK_STAT_UPDATE, trackStatMqVo);
        }
    }


}
