package com.bbs.service;

import com.bbs.Dao.UserDao;
import com.bbs.bean.User;
import com.bbs.util.MybatisUtil;
import com.easyFramework.annotation.Service;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * UserService
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Service
public class UserService {
//    @Autowired
//    private  UserDao userDao;
//
//
//    public UserService(UserDao userDao) {
//        this.userDao = userDao;
//    }
    public UserService(){

    }


    public void addUser(User user) {
//        if (user != null) {
//            userDao.addUser(user);
//        }
        SqlSession session = MybatisUtil.openSession();
        UserDao userDao = session.getMapper(UserDao.class);

        userDao.addUser(user);

        session.commit();
        if (session != null)
            session.close();

    }


    public void updateUserByUserName(User user) {
        SqlSession session = MybatisUtil.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        userDao.updateUserByUserName(user);
        session.commit();
        if (session != null)
            session.close();
    }


    public User getUserByUserName(String userName) {
        SqlSession session = MybatisUtil.openSession();
        UserDao userDao = session.getMapper(UserDao.class);
        User user=userDao.findUserByUserName(userName);
        if (session != null)
            session.close();
        return user;
    }


    public String getPassword(String userName) {
//        if (userName == null) {
//           return null;
//        }
//        return userDao.getUserPasswordByUserName(userName);
        SqlSession session = MybatisUtil.openSession();
        UserDao userDao = session.getMapper(UserDao.class);

        String pw=userDao.getUserPasswordByUserName(userName);

        if (session != null)
            session.close();

        return pw;
    }


//    public void deleteUserByUserName(String userName) { }


   public List<User> getAllUser() {
//        return userDao.getAllUserInfo();
       SqlSession session = MybatisUtil.openSession();
       UserDao userDao = session.getMapper(UserDao.class);

       List<User> users=userDao.getAllUserInfo();

       if (session != null)
           session.close();

       return users;
    }
}
