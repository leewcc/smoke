package com.smoke.service;

import com.smoke.dao.ManagerDao;
import com.smoke.entity.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Created by CHEN on 2016/11/26.
 */
@Service
public class ManagerService {

    @Autowired
    private ManagerDao managerDao;

    public Manager queryByIdAndPassword(String managerId, String password) {
        return managerDao.queryByIdAndPassword(managerId,password);
    }


    public Manager queryByCookie(String cookieValue) {
        return managerDao.queryByCookie(cookieValue);
    }

    public boolean addCookie(Manager manager) {
        return managerDao.addCookie(manager);
    }

}
