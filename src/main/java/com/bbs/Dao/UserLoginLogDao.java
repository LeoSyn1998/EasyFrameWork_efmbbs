package com.bbs.Dao;

import com.bbs.bean.UserLoginLog;

import java.util.List;

public interface UserLoginLogDao {
    /**
     * 获取所有用户的登录日志
     *
     * @return 日志列表
     */
    List<UserLoginLog> listAllUserLoginLog();

    /**
     * 添加登录日志
     *
     * @param userLoginLog 登录日志
     */
    void addUserLoginLog(UserLoginLog userLoginLog);
}
