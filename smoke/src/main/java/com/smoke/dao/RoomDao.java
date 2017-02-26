package com.smoke.dao;

import com.smoke.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CHEN on 2016/11/26.
 */
@Repository
public interface RoomDao {

    /**
     * 增加房间
     *
     * @param room
     * @return
     */
    boolean addRoom(Room room);

    /**
     * 删除房间
     *
     * @param roomId
     * @return
     */
    boolean deleteRoom(int roomId);

    /**
     * 更新房间
     *
     * @param room
     * @return
     */
    boolean updateRoom(Room room);

    /**
     * 根据老师的Id查询老师名下的房间号和名称
     *
     * @param userId 老师id
     * @return
     */
    List<Room> queryByTeacherId(int userId);


}
