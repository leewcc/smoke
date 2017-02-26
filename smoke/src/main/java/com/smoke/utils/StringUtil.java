package com.smoke.utils;

/**
 * Created by asus2015 on 2016/12/26.
 */
public class StringUtil {
    public static String toIdentification(int groupId,int equipmentId) {
        int groupEquipment = groupId  * 1000 + equipmentId;

        String data = String.valueOf(groupEquipment);

        int fillZero = 6 - data.length();

        for(int i = 0; i < fillZero; i++) {
            data = "0" + data;
        }

        return data;
    }
}
