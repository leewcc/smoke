package com.smoke.dao;

import com.smoke.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CHEN on 2016/11/26.
 */
@Repository
public interface UserDao {

    /**
     * 增加用户
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     *根据userId 删除用户
     * @param
     * @return
     */
    boolean deleteUser(int  userId);

    /**
     * 根据userId更新用户
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 查询所有的用户
     * @return
     */
    List<User> queryAllUser();

}
