package com.smoke.dao;

import com.smoke.entity.Manager;
import org.apache.ibatis.annotations.Param;

/**
 * Created by CHEN on 2016/11/26.
 */
public interface ManagerDao {

    /**
     * 使用工号和密码进行登录
     * @param managerId
     * @param password
     * @return
     */
    Manager queryByIdAndPassword(@Param("id") String managerId,@Param("password") String password);

    Manager queryByCookie(String cookieValue);

    boolean addCookie(Manager manager);
}
