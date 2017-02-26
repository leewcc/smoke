package com.smoke.service;

import com.smoke.dao.EquipmentDao;
import com.smoke.entity.Equipment;
import com.smoke.entity.Room;
import com.smoke.entity.Teacher;
import com.smoke.smoke.SharedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by asus2015 on 2016/12/1.
 */
@Service
public class EquipmentService {
    @Autowired
    EquipmentDao equipmentDao;

    public List<Equipment> getEquipmentByRoomId(int roomId) {
        return equipmentDao.queryByRoomId(roomId);
    }

    public boolean addEquipment(Equipment equipment) {
        boolean result = equipmentDao.addEquipment(equipment);

        if (result) {
            Room room = SharedObject.rooms.get((Integer) equipment.getRoomId());
            synchronized (room) {
                room.getEquipments().add(equipment);
            }

            synchronized (SharedObject.equipments) {
                SharedObject.equipments.put(equipment.getEquipmentId(), equipment);
            }
        }

        return result;
    }

    public boolean deleteEquipment(int equipmentId) {
        Equipment equipmentDeleteB = equipmentDao.queryByEquipmentId(equipmentId);
        boolean result = equipmentDao.deleteEquipment(equipmentId);

        try{
        if (result) {
            Equipment equipment = SharedObject.equipments.get(equipmentDeleteB.getEquipmentId());
            synchronized (SharedObject.equipments) {
                SharedObject.equipments.remove(equipmentId);
            }

            Room room = SharedObject.rooms.get((Integer) equipment.getRoomId());
            synchronized (room) {
                room.getEquipments().remove(equipment);
                room.getWarningEquipment().remove(equipment.getEquipmentId());
            }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean updateEquipment(Equipment equipment) {
        try {
            Equipment equipmentUpdateB = equipmentDao.queryByEquipmentId(equipment.getId());

            boolean result = equipmentDao.updateEquipment(equipment);

            if (result) {
                // 获取设备
                Equipment e = SharedObject.equipments.get(equipmentUpdateB.getEquipmentId());

                synchronized (e) {
                    if (equipment.getEquipmentName() != null) {
                        e.setEquipmentName(equipment.getEquipmentName());
                    }

                    if (equipment.getEquipmentId() != null) {
                        e.setEquipmentId(equipment.getEquipmentId());

                        synchronized (SharedObject.equipments) {
                            SharedObject.equipments.remove(equipmentUpdateB.getEquipmentId());
                            SharedObject.equipments.put(e.getEquipmentId(), e);
                        }

                        Room room = SharedObject.rooms.get(e.getRoomId());
                        List<String> warningEquipment = room.getWarningEquipment();
                        synchronized (warningEquipment) {
                            if (warningEquipment.contains(equipmentUpdateB.getEquipmentId())) {
                                warningEquipment.remove(equipmentUpdateB.getEquipmentId());
                                warningEquipment.add(e.getEquipmentId());
                            }
                        }
                    }


                }
            }


            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Equipment> queryByRoomId(int roomId) {
        return equipmentDao.queryByRoomId(roomId);
    }

}
