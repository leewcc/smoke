package com.smoke.api;

import java.util.Calendar;

/**
 * Created by CHEN on 2016/12/2.
 */
public class CurTime {

    public static  long getCurTime() {
        Calendar calendar=Calendar.getInstance();
        int zoneOffset=calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset=calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND,-(zoneOffset+dstOffset));
        return calendar.getTimeInMillis();

    }

}
