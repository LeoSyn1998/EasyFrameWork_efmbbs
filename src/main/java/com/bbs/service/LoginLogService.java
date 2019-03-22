package com.bbs.service;

import com.bbs.Dao.UserLoginLogDao;
import com.bbs.bean.UserLoginLog;
import com.bbs.util.MybatisUtil;
import com.easyFramework.annotation.Service;
import org.apache.ibatis.session.SqlSession;

/**
 * LoginLogService
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */

@Service
public class LoginLogService{
//    @Autowired
//    private  UserLoginLogDao userLoginLogDao;
//
//
//    public LoginLogService() {
//
//    }

    public LoginLogService(){

    }

    public void listAllUserLoginLog() { }

    public void addUserLoginLog(UserLoginLog userLoginLog) {
        SqlSession session = MybatisUtil.openSession();
        UserLoginLogDao userLoginLogDao = session.getMapper(UserLoginLogDao.class);
        userLoginLogDao.addUserLoginLog(userLoginLog);

        session.commit();
        if (session != null)
            session.close();
    }
}
