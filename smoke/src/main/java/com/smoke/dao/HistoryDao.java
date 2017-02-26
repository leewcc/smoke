package com.smoke.dao;

import com.smoke.entity.SmokeStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by asus2015 on 2016/12/1.
 */
@Repository
public interface HistoryDao {
    List<SmokeStatus> queryByRoomId(int roomId);

    int insertHistory(SmokeStatus smokeStatus);
}
