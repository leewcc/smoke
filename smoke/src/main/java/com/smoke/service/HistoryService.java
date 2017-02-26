package com.smoke.service;

import com.smoke.dao.HistoryDao;
import com.smoke.entity.SmokeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by asus2015 on 2016/12/1.
 */
@Service
public class HistoryService {
    @Autowired
    HistoryDao historyDao;

    public List<SmokeStatus> getHistoryByRoomId(int roomId) {
        return historyDao.queryByRoomId(roomId);
    }
}
