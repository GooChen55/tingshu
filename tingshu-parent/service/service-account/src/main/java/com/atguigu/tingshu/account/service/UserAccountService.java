package com.atguigu.tingshu.account.service;

import com.atguigu.tingshu.model.account.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface UserAccountService extends IService<UserAccount> {


    /**
     * 初始化账户记录
     * @param map 业务数据
     */
    void initUserAccount(Map<String, Object> map);
}
