package com.smoke.service;

import com.smoke.dao.PhoneDao;
import com.smoke.entity.Phone;
import com.smoke.entity.Teacher;
import com.smoke.smoke.SharedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CHEN on 2016/12/1.
 */
@Service
public class PhoneService {

    @Autowired
    private PhoneDao phoneDao;

    /**
     * 增加了手机
     *
     * @param phone
     * @return
     */
    public boolean addPhone(Phone phone) {
        boolean result = phoneDao.addPhone(phone);

        if (result) {
            Teacher teacher = SharedObject.teachers.get((Integer) phone.getUserId());
            if (teacher != null) {
                synchronized (teacher) {
                    teacher.getPhones().add(phone);
                }
            }
        }

        return result;
    }

    /**
     * @param userPhone 手机号
     * @return
     */
    public boolean deletePhone(int id,int userId,String userPhone) {
        boolean result = phoneDao.deletePhone(id);

        if (result) {
            Teacher teacher = SharedObject.teachers.get((Integer) userId);

            if (teacher != null) {
                synchronized (teacher) {
                    Phone phone = new Phone();
                    phone.setUserPhone(userPhone);
                    phone.setUserId(userId);
                    teacher.getPhones().remove(phone);
                }
            }
        }

        return result;
    }

    /**
     * @param phone
     * @return
     */
    public boolean updatePhone(Phone phone) {
        boolean result = phoneDao.updatePhone(phone);

        try {
            if (result) {
                 // 获取老师
                Teacher teacher = SharedObject.teachers.get(phone.getUserId());

                if (teacher != null) {
                    List<Phone> phones = teacher.getPhones();
                    int index = phones.indexOf(phone);
                    if (index != -1) {

                        Phone p = phones.get(index);
                        synchronized (p) {
                            p.setUserPhone(phone.getUserPhone());
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 返回所有老师的电话
     *
     * @param userId
     * @return
     */
    public List<Phone> queryByUserId(int userId) {
        return phoneDao.queryByUserId(userId);
    }


}
