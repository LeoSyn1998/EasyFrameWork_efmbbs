package com.bbs.controller;

import com.bbs.bean.User;
import com.bbs.bean.UserLoginLog;
import com.bbs.service.LoginLogService;
import com.bbs.service.UserService;
import com.easyFramework.annotation.Autowired;
import com.easyFramework.annotation.Controller;
import com.easyFramework.annotation.RequestMapping;
import com.easyFramework.annotation.RequestParam;
import com.easyFramework.bean.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * UserController
 *
* @author Syn
 * @version 1.0
 * @since 2018
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    LoginLogService loginLogService;


    public UserController() {
    }

    /**
     * 用户登录
     *
     */
    @RequestMapping(value = "/userLogin", method = "post")
    public ModelAndView userLogin(@RequestParam("userName") String username, @RequestParam("password") String password, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        // 通过用户名查找User对象
        User user = userService.getUserByUserName(username);
        String pw = "";
        if (user != null) {
            pw = userService.getPassword(user.getUserName());
        }
        // 判断登录信息是否正确
        if (user != null && password.equals(pw)) {

            // 获取登录基本信息
            String userName = user.getUserName();
            Timestamp lastLoginTime = new Timestamp(System.currentTimeMillis());

            // 更新用户信息
            user.setLastLoginTime(lastLoginTime);
            user.setCredit(5 + user.getCredit());
            userService.updateUserByUserName(user);

            // 更新用户登录日志
            UserLoginLog userLoginLog = new UserLoginLog();
            userLoginLog.setUserName(userName);
            userLoginLog.setLoginDateTime(lastLoginTime);
            loginLogService.addUserLoginLog(userLoginLog);

            // 登陆成功，跳转到主页
            modelAndView.setPath("/index.jsp");
            request.getSession().setAttribute("username", user.getUserName());
            modelAndView.addModel("userName", user.getUserName());
            return modelAndView;
        }

        // 登录失败，跳转页面
        modelAndView.setPath("error.jsp");
        return modelAndView;
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/register", method = "post")
    public ModelAndView userRegister(@RequestParam("userName") String username,
                               @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();

        if (username != null) {
            // 如果数据库中没有该用户，可以注册，否则跳转页面
            if (userService.getUserByUserName(username) == null) {

                // 添加用户
                User user = new User();
                user.setUserName(username);
                user.setPassword(password);

                Timestamp createLoginTime = new Timestamp(System.currentTimeMillis());
                user.setCreateTime(createLoginTime);
                user.setLastLoginTime(createLoginTime);
                userService.addUser(user);

                // 添加用户登录日志
                UserLoginLog userLoginLog = new UserLoginLog();
                userLoginLog.setUserName(username);
                userLoginLog.setLoginDateTime(createLoginTime);
                loginLogService.addUserLoginLog(userLoginLog);

                // 注册成功跳转
                modelAndView.setPath("index.jsp");
                modelAndView.addModel("userName", username);
                return modelAndView;
            } else {
                modelAndView.setPath("error.jsp");
                return modelAndView;
            }
        } else {
            modelAndView.setPath("error.jsp");
            return modelAndView;
        }
    }

    /**
     * 显示个人信息
     */
    @RequestMapping("/listUserInfo")
    public ModelAndView listUserInfo(@RequestParam("username")String username) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserByUserName(username);

        modelAndView.setPath("/user/userInfo.jsp");
        modelAndView.addModel("user",user);
        return modelAndView;
    }

    /**
     * 修改个人信息页面
     */
    @RequestMapping(value = "/updateUserInfo",method = "post")
    public ModelAndView userUpdateInfoPage(@RequestParam("userName")String userName,@RequestParam("userPhone")int userPhone,@RequestParam("userEmail")String userEmail) {
        ModelAndView modelAndView = new ModelAndView();
;
        User user = userService.getUserByUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserPhone(userPhone);
        userService.updateUserByUserName(user);
        modelAndView.setPath("/user/userInfo.jsp");
        modelAndView.addModel("user",user);
        return modelAndView;
    }

    /**
     * 用户注销功能
     *
     * @param request 请求
     * @return 返回页面
     */
    @RequestMapping(value = "/loginOut")
    public ModelAndView loginOut(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        request.getSession().removeAttribute("username");
        modelAndView.setPath("index.jsp");
        return modelAndView;
    }
}
