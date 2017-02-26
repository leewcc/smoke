package com.smoke.service;

import com.smoke.dao.UserDao;
import com.smoke.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CHEN on 2016/12/1.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public List<User> queryAllUser() {
        return userDao.queryAllUser();
    }




}
