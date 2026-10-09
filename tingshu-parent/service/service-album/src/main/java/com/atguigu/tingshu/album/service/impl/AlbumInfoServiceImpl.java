package com.atguigu.tingshu.album.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.atguigu.tingshu.album.mapper.AlbumAttributeValueMapper;
import com.atguigu.tingshu.album.mapper.AlbumInfoMapper;
import com.atguigu.tingshu.album.mapper.AlbumStatMapper;
import com.atguigu.tingshu.album.mapper.TrackInfoMapper;
import com.atguigu.tingshu.album.service.AlbumAttributeValueService;
import com.atguigu.tingshu.album.service.AlbumInfoService;
import com.atguigu.tingshu.common.constant.SystemConstant;
import com.atguigu.tingshu.common.execption.GuiguException;
import com.atguigu.tingshu.common.rabbit.constant.MqConst;
import com.atguigu.tingshu.common.rabbit.service.RabbitService;
import com.atguigu.tingshu.model.album.AlbumAttributeValue;
import com.atguigu.tingshu.model.album.AlbumInfo;
import com.atguigu.tingshu.model.album.AlbumStat;
import com.atguigu.tingshu.model.album.TrackInfo;
import com.atguigu.tingshu.query.album.AlbumInfoQuery;
import com.atguigu.tingshu.vo.album.AlbumAttributeValueVo;
import com.atguigu.tingshu.vo.album.AlbumInfoVo;
import com.atguigu.tingshu.vo.album.AlbumListVo;
import com.atguigu.tingshu.vo.album.AlbumStatVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.atguigu.tingshu.common.constant.SystemConstant.*;

@Slf4j
@Service
@SuppressWarnings({"all"})
public class AlbumInfoServiceImpl extends ServiceImpl<AlbumInfoMapper, AlbumInfo> implements AlbumInfoService {

    @Autowired
    private AlbumInfoMapper albumInfoMapper;

    @Autowired
    private AlbumAttributeValueService albumAttributeValueService;

    @Autowired
    private AuditServiceImpl auditService;

    @Autowired
    private RabbitService rabbitService;

    /***
     * 保存专辑信息
     * @param albumInfoVo 专辑VO信息
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class) //默认捕获到RuntimeExcetion跟Error，会进行事务回滚 放大捕获范围
    public void saveAlbumInfo(AlbumInfoVo albumInfoVo, Long userId) {
        //1.保存专辑信息
        //1.1 将专辑VO实体转为专辑PO对象
        AlbumInfo albumInfo = BeanUtil.copyProperties(albumInfoVo, AlbumInfo.class);
        //1.2 为专辑必须属性手动赋值 用户ID、免费试听集数、审核状态
        albumInfo.setUserId(userId);
        albumInfo.setTracksForFree(5);
        albumInfo.setStatus(SystemConstant.ALBUM_STATUS_NO_PASS);
        //1.3 保存专辑
        albumInfoMapper.insert(albumInfo);
        //1.4 获取保存的专辑ID
        Long albumId = albumInfo.getId();

        //2.保存专辑标签关系
        List<AlbumAttributeValueVo> albumAttributeValueVoList = albumInfoVo.getAlbumAttributeValueVoList();
        if (CollUtil.isNotEmpty(albumAttributeValueVoList)) {
            List<AlbumAttributeValue> albumAttributeValueList = albumAttributeValueVoList.stream().map(vo -> {
                AlbumAttributeValue albumAttributeValue = BeanUtil.copyProperties(vo, AlbumAttributeValue.class);
                albumAttributeValue.setAlbumId(albumId);
                return albumAttributeValue;
            }).collect(Collectors.toList());
            albumAttributeValueService.saveBatch(albumAttributeValueList);
        }
        //3.新增专辑统计数值
        this.saveAlbumInfoStat(albumId, ALBUM_STAT_PLAY, 0);
        this.saveAlbumInfoStat(albumId, ALBUM_STAT_SUBSCRIBE, 0);
        this.saveAlbumInfoStat(albumId, ALBUM_STAT_BUY, 0);
        this.saveAlbumInfoStat(albumId, ALBUM_STAT_COMMENT, 0);

        //4.对文本进行内容审核
        String text = albumInfo.getAlbumTitle() + albumInfo.getAlbumIntro();
        String suggestion = auditService.audit_text(text);
        if (StringUtils.isNotBlank(suggestion)) {
            if ("block".equals(suggestion)) {
                albumInfo.setStatus(ALBUM_STATUS_NO_PASS);
            } else if ("review".equals(suggestion)) {
                albumInfo.setStatus(ALBUM_STATUS_MANUAL);
            } else if ("pass".equals(suggestion)) {
                albumInfo.setStatus(ALBUM_STATUS_PASS);
                //TODO 审核通过专辑才可以"同步"数据到ElasticSearch
                rabbitService.sendMessage(MqConst.EXCHANGE_ALBUM, MqConst.ROUTING_ALBUM_UPPER, albumId);
            }
            albumInfoMapper.updateById(albumInfo);
        }
    }

    @Autowired
    private AlbumStatMapper albumStatMapper;

    /**
     * 保存专辑统计信息
     *
     * @param albumId  专辑ID
     * @param statType 统计类型
     * @param statNum  统计数值 0401-播放量 0402-订阅量 0403-购买量 0403-评论数'
     */
    @Override
    public void saveAlbumInfoStat(Long albumId, String statType, int statNum) {
        AlbumStat albumStat = new AlbumStat();
        albumStat.setAlbumId(albumId);
        albumStat.setStatType(statType);
        albumStat.setStatNum(statNum);
        albumStatMapper.insert(albumStat);
    }

    /**
     * 查看当前用户专辑分页列表（包含统计信息）
     *
     * @param pageInfo MP分页对象
     * @param query    查询条件
     * @return MP分页对象
     */
    @Override
    public IPage<AlbumListVo> findUserAlbumPage(IPage<AlbumListVo> pageInfo, AlbumInfoQuery query) {
        return albumInfoMapper.findUserAlbumPage(pageInfo, query);
    }


    @Autowired
    private TrackInfoMapper trackInfoMapper;

    /**
     * 删除专辑
     *
     * @param id 专辑ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAlbumInfo(Long id) {
        //1.判断该专辑下是否有声音
        Long count = trackInfoMapper.selectCount(
                new LambdaQueryWrapper<TrackInfo>()
                        .eq(TrackInfo::getAlbumId, id)
        );
        if (count > 0) {
            throw new GuiguException(500, "该专辑下关联声音");
        }
        //2.删除专辑
        albumInfoMapper.deleteById(id);

        //3.删除专辑标签关系
        albumAttributeValueService.remove(
                new LambdaQueryWrapper<AlbumAttributeValue>()
                        .eq(AlbumAttributeValue::getAlbumId, id)
        );

        //4.删除统计信息
        albumStatMapper.delete(
                new LambdaQueryWrapper<AlbumStat>()
                        .eq(AlbumStat::getAlbumId, id)
        );
        //5.基于MQ异步删除ES中专辑文档
        rabbitService.sendMessage(MqConst.EXCHANGE_ALBUM, MqConst.ROUTING_ALBUM_LOWER, id);

    }

    /**
     * 根据专辑ID查询专辑信息（包含标签列表）
     *
     * @param id 专辑ID
     * @return
     */
    @Override
    public AlbumInfo getAlbumInfo(Long id) {
        //1.根据专辑ID查询专辑信息
        //albumInfoMapper.selectById(id);
        //baseMapper.selectById(id);
        AlbumInfo albumInfo = this.getById(id);
        //2.根据专辑ID查询专辑标签
        List<AlbumAttributeValue> attributeValues = albumAttributeValueService.list(
                new LambdaQueryWrapper<AlbumAttributeValue>()
                        .eq(AlbumAttributeValue::getAlbumId, id)
        );
        if (CollUtil.isNotEmpty(attributeValues)) {
            albumInfo.setAlbumAttributeValueVoList(attributeValues);
        }
        return albumInfo;
    }

    /**
     * 更新专辑信息
     *
     * @param id          专辑ID
     * @param albumInfoVo 专辑VO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAlbumInfo(Long id, AlbumInfoVo albumInfoVo) {
        //1.修改专辑信息
        AlbumInfo albumInfo = BeanUtil.copyProperties(albumInfoVo, AlbumInfo.class);
        albumInfo.setStatus(ALBUM_STATUS_NO_PASS);
        albumInfo.setId(id);
        albumInfoMapper.updateById(albumInfo);

        //2.修改专辑标签关系
        //2.1 根据专辑ID删除原有标签关系
        albumAttributeValueService.remove(
                new LambdaQueryWrapper<AlbumAttributeValue>()
                        .eq(AlbumAttributeValue::getAlbumId, id)
        );
        //2.2 添加新的标签关系
        List<AlbumAttributeValueVo> albumAttributeValueVoList = albumInfoVo.getAlbumAttributeValueVoList();
        if (CollUtil.isNotEmpty(albumAttributeValueVoList)) {
            List<AlbumAttributeValue> albumAttributeValueList = albumAttributeValueVoList.stream().map(vo -> {
                AlbumAttributeValue albumAttributeValue = BeanUtil.copyProperties(vo, AlbumAttributeValue.class);
                albumAttributeValue.setAlbumId(id);
                return albumAttributeValue;
            }).collect(Collectors.toList());
            albumAttributeValueService.saveBatch(albumAttributeValueList);
        }
        //4. 对文本进行内容审核
        String text = albumInfo.getAlbumTitle() + albumInfo.getAlbumIntro();
        String suggestion = auditService.audit_text(text);
        if (StringUtils.isNotBlank(suggestion)) {
            if ("block".equals(suggestion)) {
                albumInfo.setStatus(ALBUM_STATUS_NO_PASS);
            } else if ("review".equals(suggestion)) {
                albumInfo.setStatus(ALBUM_STATUS_MANUAL);
            } else if ("pass".equals(suggestion)) {
                albumInfo.setStatus(ALBUM_STATUS_PASS);
                //审核通过专辑才可以同步数据到ElasticSearch
                rabbitService.sendMessage(MqConst.EXCHANGE_ALBUM, MqConst.ROUTING_ALBUM_UPPER, albumInfo.getId());
            }
            albumInfoMapper.updateById(albumInfo);
        }
    }

    /**
     * 查询指定用户专辑列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<AlbumInfo> findUserAllAlbumList(Long userId) {
        //1.构建查询条件
        LambdaQueryWrapper<AlbumInfo> queryWrapper = new LambdaQueryWrapper<>();
        //1.1 查询条件：用户ID
        queryWrapper.eq(AlbumInfo::getUserId, userId);
        //1.2 指定查询字段
        queryWrapper.select(AlbumInfo::getId, AlbumInfo::getAlbumTitle);
        //1.3 指定排序字段
        queryWrapper.orderByDesc(AlbumInfo::getCreateTime);
        //1.4 限制返回数量 TODO 返回太多造成前端卡顿
        queryWrapper.last("limit 100");

        //2.执行调用
        return albumInfoMapper.selectList(queryWrapper);
    }

    /**
     * 根据专辑ID查询统计信息
     * @param albumId
     * @return
     */
    @Override
    public AlbumStatVo getAlbumStatVo(Long albumId) {
        return albumInfoMapper.getAlbumStatVo(albumId);
    }
}
