package com.smoke.websocket;

import com.smoke.entity.*;
import com.smoke.smoke.SharedObject;
import com.smoke.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该类定义在建立 websocket 连接后，服务器将如何处理用户请求
 * Created by asus2015 on 2016/11/29.
 */
@Component
public class SmokeWebSocketHandler implements WebSocketHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(SmokeWebSocketHandler.class);

    // 键 - 老师工号  值 - 查看该老师设备信息的所有 websocket 连接
    // 系统开始时，需提前初始化所有老师的信息添加进入集合
    public static Map<Integer, List<WebSocketSession>> map;

    /**
     * 连接建立后，需要将连接添加进查看对应的老师工号的连接集合众
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        // 获取老师工号
        int tid = (Integer)webSocketSession.getAttributes().get("userId");

        // 根据老师工号获取对应的连接集合
        List<WebSocketSession> sessions = map.get((Integer)tid);

        // 判断是否存在该老师的连接集合，若用户不存在，则直接关闭此连接
        if( sessions == null) {
            Teacher teacher = SharedObject.teachers.get((Integer)tid);
            if(teacher != null) {
                synchronized (map) {
                    sessions = new ArrayList<>();
                    map.put(tid,sessions);
                }

            }else {
                webSocketSession.close();
                return;
            }
        }else {
            sessions.add(webSocketSession);
        }

        Teacher teacher = SharedObject.teachers.get(tid);
        List<Room> rooms =teacher.getRooms();

        SmokeWebSocketHandler.sendMessage(tid, JacksonUtil.jsonToMap(new Object[]{"choice","teaId","teacher","rooms"}, new Object[]{0,teacher.getUserId(),teacher,rooms}));
    }

    /**
     * 处理用户发来的命令
     * 命令的种类：
     *      1. see_room 查看房间状态
     *      2. see_equipment 查看房间设备状态
     *      3. see_history 查看房间历史状态
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
      try {

          // 获取用户发送过来的命令
          String message = webSocketMessage.getPayload().toString();

          LOGGER.debug("客户端发送过来的命令是" + message);

          int userId = (Integer) webSocketSession.getAttributes().get("userId");

          // 分解命令  命令格式：command/:roomId
          String[] takon = message.split("/");
          String command = takon[0];
          int roomId = Integer.parseInt(takon[1]);



          // 根据命令来做相应的处理
          switch (command) {
              case "see_room":
                  seeRoom(userId);
                  break;

              case "see_equipment":
                  seeEquipment(userId, roomId);
                  break;

              case "see_history":
                  seeHistory(userId, roomId);
                  break;
          }
      }catch (Exception e) {
          e.printStackTrace();
      }
    }

    private static void seeRoom(int userId) throws Exception {
        String status = "ERROR";

        // 根据用户id获取用户
        Teacher teacher = SharedObject.teachers.get((Integer)userId);

        // 用户不存在，则立即发送没有房间的信息
        if(teacher == null) {
            // 发送空信息,用户不存在，码为 ERROR
            sendMessage(userId, JacksonUtil.jsonToMap(new Object[]{"statusCode"}, new Object[]{"ERROR"}));
            return;
        } else {
            // 发送具有房间的信息，码为 success
            status = "SUCCESS";
            sendMessage(userId, JacksonUtil.jsonToMap(new Object[]{"choice","teaId","statusCode","rooms"}, new Object[]{1, teacher.getUserId(),"SUCCESS", teacher.getRooms()}) );
        }

    }

    private static void seeEquipment(int userId, int roomId) throws  Exception{
        String status = "ERROR";

        // 获取房间
        Room room = SharedObject.rooms.get((Integer)roomId);

        if(room == null) {
            // 发送空信息，房间不存在，码为 ERROR
            sendMessage(userId, JacksonUtil.jsonToMap(new Object[]{"statusCode"}, new Object[]{"ERROR"}));

        }else {
            // 发送设备信息，码为 SUCCESS
            status = "SUCCESS";
            List<Equipment> equipments = room.getEquipments();
            sendMessage(userId, JacksonUtil.jsonToMap(new Object[]{"choice","teaId","statusCode","equipments"}, new Object[]{2,userId, "SUCCESS", equipments}));
        }
    }

    private static void seeHistory(int userId,int roomId) throws  Exception {
        String status = "ERROR";

        // 获取房间
        Room room = SharedObject.rooms.get((Integer)roomId);

        if(room == null) {
            // 发送空信息，房间不存在，码为 ERROR
            sendMessage(userId, JacksonUtil.jsonToMap(new Object[]{"statusCode"}, new Object[]{"ERROR"}));

        }else {
            // 发送历史信息，码为 SUCCESS
            status = "SUCCESS";
            List<SmokeStatus> historys = room.getHistorys();
            List<History> histories = new ArrayList<>();
            Map<Long,History> historyMap = new HashMap<>();
            for(SmokeStatus history : historys) {
                Long time = history.getRecordTime();
                History timeToHistory = historyMap.get((Long)time);

                if(timeToHistory == null) {
                    timeToHistory = new History(time);
                    historyMap.put(time,timeToHistory);
                    histories.add(timeToHistory);
;                }

                timeToHistory.getRecords().add(history);
            }
            sendMessage(userId, JacksonUtil.jsonToMap(new Object[]{"choice", "teaId", "statusCode","historys"}, new Object[]{3,userId,"SUCCESS",histories}));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    /**
     * 连接断开后，需要将 webSocketSession 从 map 集合中移除
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        // 获取老师工号
        int tid = (Integer)webSocketSession.getAttributes().get("userId");

        // 获取对应工号的 session 集合
        List<WebSocketSession> sessions = map.get((Integer)tid);

        if(sessions == null) {
            ;
        } else {
            sessions.remove(webSocketSession);
        }

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public static void sendMessage(int userId, String message)  throws Exception{
        List<WebSocketSession> sessions = SmokeWebSocketHandler.map.get((Integer)userId);

        // 判断是否有用户与其相连,若没，则忽略此次信息，将其保存在用户中
        if(sessions != null && sessions.size() > 0) {
            // 遍历所有 websocket 连接，将信息推送给用户
            Iterator<WebSocketSession> iterator = sessions.iterator();
            while(iterator.hasNext()) {
                WebSocketSession session = iterator.next();

                // 执行发送
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    if(session.isOpen()) {
                        LOGGER.warn("有连接已经中断");
                        iterator.remove();
                    }
                    e.printStackTrace();
                }
            }

        }
    }
}
