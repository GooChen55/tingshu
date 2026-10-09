package com.atguigu.tingshu.user.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.atguigu.tingshu.common.constant.RedisConstant;
import com.atguigu.tingshu.common.rabbit.constant.MqConst;
import com.atguigu.tingshu.common.rabbit.service.RabbitService;
import com.atguigu.tingshu.model.user.UserInfo;
import com.atguigu.tingshu.model.user.UserPaidAlbum;
import com.atguigu.tingshu.model.user.UserPaidTrack;
import com.atguigu.tingshu.user.mapper.UserInfoMapper;
import com.atguigu.tingshu.user.mapper.UserPaidAlbumMapper;
import com.atguigu.tingshu.user.mapper.UserPaidTrackMapper;
import com.atguigu.tingshu.user.service.UserInfoService;
import com.atguigu.tingshu.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings({"all"})
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private UserPaidAlbumMapper userPaidAlbumMapper;

    @Autowired
    private UserPaidTrackMapper userPaidTrackMapper;

    /**
     * 微信一键登录
     *
     * @param code 小程序端对接微信获取临时登录凭证code 5分钟只能使用一次
     * @return {token:"访问令牌"}
     */
    @Override
    public Map<String, String> wxLogin(String code) {
        try {
            //1. 对接微信获取微信账户唯一标识
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            Assert.notNull(sessionInfo, "登录,code:{}失败", code);
            String wxOpenId = sessionInfo.getOpenid();

            //2. 根据唯一标识查询本地用户信息
            UserInfo userInfo = userInfoMapper.selectOne(
                    new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getWxOpenId, wxOpenId)
            );

            //3. 如果用户信息为空
            if (userInfo == null) {
                //3.1 新增用户信息（关联微信唯一标识）
                userInfo = new UserInfo();
                userInfo.setNickname("听友" + IdUtil.nanoId());
                userInfo.setAvatarUrl("http://1255727855.vod-qcloud.com/9cbe3378vodsh1255727855/5a39afe35001834806587349151/waeazMZXQp0A.png");
                userInfo.setWxOpenId(wxOpenId);
                userInfoMapper.insert(userInfo);
                //3.2 TODO 基于RabbitMQ隐式初始化账号（余额）信息
                //3.2.1 准备初始化账户对象采用Map
                Map<String, Object> map = new HashMap<>();
                map.put("userId", userInfo.getId());
                map.put("amount", new BigDecimal("1000.00"));
                map.put("orderNo", "ZS" + IdUtil.getSnowflakeNextIdStr());
                map.put("title", "新用户注册赠送");
                //3.2.2 调用Rabbit生产者工具类发送消息  对象必须实现序列化接口
                rabbitService.sendMessage(MqConst.EXCHANGE_USER, MqConst.ROUTING_USER_REGISTER, map);
            }

            //4. 基于用户信息生成令牌,将令牌、用户基本信息 存入Redis
            //4.1 构建Redis登录信息key 采用UUID做为token
            String token = IdUtil.fastUUID();
            String loginKey = RedisConstant.USER_LOGIN_KEY_PREFIX + token;
            //4.2 将用户信息转为VO
            UserInfoVo userInfoVo = BeanUtil.copyProperties(userInfo, UserInfoVo.class);
            //4.3 存入Redis
            redisTemplate.opsForValue().set(loginKey, userInfoVo, RedisConstant.USER_LOGIN_KEY_TIMEOUT, TimeUnit.SECONDS);
            //5. 响应登录成功令牌
            return Map.of("token", token);
        } catch (WxErrorException e) {
            log.error("微信登录失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserInfoVo getUserInfoVo(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        return BeanUtil.copyProperties(userInfo, UserInfoVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long userId, UserInfoVo userInfoVo) {
        //只允许修改基本信息 昵称、头像
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setAvatarUrl(userInfoVo.getAvatarUrl());
        userInfo.setNickname(userInfoVo.getNickname());
        userInfoMapper.updateById(userInfo);
    }


    /**
     * 检查每个提交声音购买状态，如果已购买将购买状态设置为1，反之设置为0
     *
     * @param userId                        用户ID
     * @param albumId                       专辑ID
     * @param needCheckPayStatusTrackIdList 待检查购买状态声音ID列表
     * @return 每个声音购买状态 {声音ID:购买状态}
     */
    @Override
    public Map<Long, Integer> userIsPaidTrack(Long userId, Long albumId, List<Long> needCheckPayStatusTrackIdList) {
        //1.根据用户ID+专辑ID 查询已购专辑表
        Long count = userPaidAlbumMapper.selectCount(
                new LambdaQueryWrapper<UserPaidAlbum>()
                        .eq(UserPaidAlbum::getUserId, userId)
                        .eq(UserPaidAlbum::getAlbumId, albumId)
        );

        //如果购买专辑，则将所有声音购买状态设置1，返回即可
        HashMap<Long, Integer> map = new HashMap<>();
        if (count > 0) {
            for (Long trackId : needCheckPayStatusTrackIdList) {
                map.put(trackId, 1);
            }
            return map;
        }

        //2.根据用户ID+专辑ID 查询已购声音表
        List<UserPaidTrack> userPaidTrackList = userPaidTrackMapper.selectList(
                new LambdaQueryWrapper<UserPaidTrack>()
                        .eq(UserPaidTrack::getUserId, userId)
                        .eq(UserPaidTrack::getAlbumId, albumId)
                        .select(UserPaidTrack::getTrackId)
        );
        //2.1 如果不存在声音购买记录，则将所有声音购买状态设置0，返回即可。
        if (CollUtil.isEmpty(userPaidTrackList)) {
            //说明当前用户未购买专辑且未购买任何声音
            for (Long trackId : needCheckPayStatusTrackIdList) {
                map.put(trackId, 0);
            }
            return map;
        }
        //2.2 如果存在声音购买记录，找出已购买（购买状态设置为1）以及未购买（购买状态设置为0）设置相应购买状态
        List<Long> userPaidTrackIdList = userPaidTrackList.stream()
                .map(UserPaidTrack::getTrackId).collect(Collectors.toList());
        //2.3 循环待检查购买状态声音ID列表
        for (Long trackId : needCheckPayStatusTrackIdList) {
            if (userPaidTrackIdList.contains(trackId)) {
                //包含在已购声音ID中
                map.put(trackId, 1);
            } else {
                map.put(trackId, 0);
            }
        }
        return map;
    }
}
