package com.smoke.dao;

import com.smoke.entity.Phone;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CHEN on 2016/11/26.
 */
@Repository
public interface PhoneDao {

    /**
     * 添加一个电话号码
     * @param phone
     * @return
     */
    boolean addPhone(Phone phone);

    /**
     * 删除一个电
     * @return
     */
    boolean deletePhone(@Param("id") int id);

    /**
     * 更新一个电话
     * @param phone
     * @return
     */
    boolean updatePhone(Phone phone);

    /**
     * 根据老师编号查询老师名下的电话
     * @param userId
     * @return
     */
    List<Phone> queryByUserId(int userId);


}
