/**
 * Copyright (C), 2015-2018，XXX人名
 * FileName: MybatisUtil
 * Author:   Administrator
 * Date:     2018/4/10 15:10
 * Description: mybatis工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.bbs.util;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 〈一句话功能简述〉<br> 
 * 〈mybatis工具类〉
 *
 * @author Administrator
 * @create 2018/4/10
 * @since 1.0.0
 */
public class MybatisUtil {
    private static SqlSessionFactory sessionFactory;

    public static SqlSession openSession() {
        try {
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            InputStream inputStream = Resources.getResourceAsStream("config.xml");
            sessionFactory  = builder.build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory.openSession();
    }
}
