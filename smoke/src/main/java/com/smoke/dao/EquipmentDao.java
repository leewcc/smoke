package com.smoke.dao;

import com.smoke.entity.Equipment;
import org.springframework.stereotype.Repository;

import java.util.List;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by CHEN on 2016/11/26.
 */
@Repository
public interface EquipmentDao {


    /**
     * 添加一台设备
     * 包含他的手动输入编号、设备名字、设备归属房间
     * @param equipment 设备
     * @return
     */
    boolean addEquipment(Equipment equipment);

    /**
     * 删除一台设备
     * @param equipmentId 设备的编号
     * @return
     */
    boolean deleteEquipment(int equipmentId);

    /**
     * 根据equipment的标号对
     * @param equipment 设备
     * @return
     */
    boolean updateEquipment(Equipment equipment);


    /**
     * 根据房间的Id 将房间下的所有机子都查出来
     * 每个设备中包含他的编号 和他的名字
     * @param roomId 房间编号
     * @return
     */
    List<Equipment> queryByRoomId(int roomId);

    Equipment queryByEquipmentId(int id);


}
