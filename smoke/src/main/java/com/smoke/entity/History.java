package com.smoke.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus2015 on 2016/12/8.
 */
public class History {
    private long recordTime;
    private List<SmokeStatus> records;

    public History(long recordTime) {
        this.recordTime = recordTime;
        this.records = new ArrayList<>();
    }


    public List<SmokeStatus> getRecords() {
        return records;
    }

    public void setRecords(List<SmokeStatus> records) {
        this.records = records;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }
}
