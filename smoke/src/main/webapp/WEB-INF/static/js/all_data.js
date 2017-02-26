
var href = window.location.href;
var index = href.lastIndexOf('=');
var tea_id = href.slice(index + 1, href.length);
var ipconfig = window.location.hostname;
function printf(str){
  // console.log(str);
}
if(window.location.href.indexOf('login') === -1 && 
  window.location.href.indexOf('manage') === -1
  && window.location.href.indexOf('updata') === -1 ){
  var socket = new WebSocket('ws://' + ipconfig + ':8080/ws?userId=' + tea_id);
  var handler = function(e) {
    console.log('server' + e.data);
    var data = JSON.parse(e.data);
    var equi_new = null,
      equi_old, room_old,target;
    var new_obj = null;
    var i, j, k, status;
    if (data.teaId == tea_id) {
      if (data.choice == 0) {
        // 刚接手完
        all_data.tea = data.teacher;

        all_data.rooms = data.rooms;
      } else if (data.choice == 2) {
        equi_new = data.equipments;
        all_data.equipments = [];

        for(k = 0; k < equi_new.length; k++){
          all_data.equipments.splice(all_data.equipments.length, 0, {
            equipmentId: equi_new[k].equipmentId,
            roomId: equi_new[k].roomId,
            equipmentName: equi_new[k].equipmentName,
            status: equi_new[k].status,
            pm1_0: equi_new[k].pm1_0,
            pm2_5: equi_new[k].pm2_5,
            pm10: equi_new[k].pm10,
            recordTime: equi_new[k].recordTime
          });
        }
        // all_data.equipments = equi_new;
        // 找到当前的房间
        for (i = 0; i < all_data.rooms.length; i++) {
          if (all_data.roomId == all_data.rooms[i].roomId) {
            target = all_data.rooms[i];
            break;
          }
        }
        // 这个是在进入房间的设备时候有的，这时候可以获取到当前的房间的id，那么就可以知道设备的房间号了。
        // 所以如果房间的状态是异常的，我们就可以遍历设备，插入警告的设备
        for (j = 0; j < equi_new.length; j++) {
          if (equi_new[j].status == 1) {
            all_data.rooms[i].warnEqu = add_arr(all_data.rooms[i].warnEqu, equi_new[j].equipmentId);
          }
        }
      } else if (data.choice == 3) {
        all_data.historys = data.historys;
      } else if (data.choice == 4) {
        // 检查并更新视图，接受到新数据
        // 考虑情况：
        // 1. 房间是正常的，突然来了个不正常的设备，然后要求改房间状态
        // 2. 当设备变成正常之后房间的状态如何改变，
        //  如何根据后台的设备和房间绑定来进行改变
        // 缺乏一个状态就是当前状态还没有获取到信息的时候
        equi_new = data.smokeStatus;
        equi_old = all_data.equipments;
        room_old = all_data.rooms;
        status = false;
        for (i = 0; i < equi_new.length; i++) {
          status = false;
          for (j = 0; j < equi_old.length; j++) {
            if (equi_new[i].equipmentId == equi_old[j].equipmentId) {
              status = true;
              // 在此进行更新，如果修改不成功再另外打算

              all_data.equipments[j].roomId = equi_new[i].roomId;
              all_data.equipments[j].status = equi_new[i].status;
              all_data.equipments[j].equipmentName = equi_new[i].equipmentName;
              all_data.equipments[j].pm1_0 = equi_new[i].pm1_0;
              all_data.equipments[j].pm2_5 = equi_new[i].pm2_5;
              all_data.equipments[j].pm10 = equi_new[i].pm10;

              // 更新房间状态
              for (k = 0; k < room_old.length; k++) {
                if (equi_old[j].roomId == room_old[k].roomId) {
                  // 找到设备相符合的房间了，
                  // 如果房间是正常的就判断是否有异常，
                  // 如果是异常的就判断是否都是正常的
                  if (equi_old[j].status != room_old[k].roomStatus) {
                    // 如果设备不正常，房间正常，房间的状态改变，
                    // 同时推进一个表，这个表记录不正常的设备。
                    if (equi_old[j].status == 1 && room_old[k].roomStatus == 0) {
                      all_data.rooms[k].roomStatus = equi_old[j].status;
                      all_data.rooms[k].warnEqu = add_arr(all_data.rooms[k].warnEqu, equi_old[j].equipmentId);
                    } else {
                      // 如果设备正常，房间不正常，检查有没有在这个表里面
                      if ((!room_old[k].warnEqu ) || push_arr(all_data.rooms[k].warnEqu, equi_old[j].equipmentId) == 0) {
                        all_data.rooms[k].roomStatus = 0;
                      }
                    }
                  } else {
                    // 如果这两个表的状态一样为异常
                    if (equi_old[j].status == 1) {
                      all_data.rooms[k].warnEqu = add_arr(all_data.rooms[k].warnEqu, equi_old[j].equipmentId);
                    }
                  }
                }
                if (!room_old[k].warnEqu ) {
                  all_data.rooms[k].roomStatus = 0;
                }
              }
            }
          }
          // 找不到这个新的设备
          if (status == false ) {
            // 将这个设备添加到equiment里面
            all_data.equipments.push(equi_new[i]);
            // 更新房间状态
            for (k = 0; k < room_old.length; k++) {
              if (equi_old[j].roomId == room_old[k].roomId) {
                // 找到设备相符合的房间了，
                // 如果房间是正常的就判断是否有异常，
                // 如果是异常的就判断是否都是正常的
                if (equi_old[j].status != room_old[k].roomStatus) {
                  // 如果设备不正常，房间正常，房间的状态改变，
                  // 同时推进一个表，这个表记录不正常的设备。
                  if (equi_old[j].status == 1 && room_old[k].roomStatus == 0) {
                    all_data.rooms[k].roomStatus = equi_old[j].status;
                    all_data.rooms[k].warnEqu = add_arr(all_data.rooms[k].warnEqu, equi_old[j].equipmentId);
                  } else {
                    // 如果设备正常，房间不正常，检查有没有在这个表里面
                    if ((!room_old[k].warnEqu ) || push_arr(all_data.rooms[k].warnEqu, equi_old[j].equipmentId) == 0) {
                      all_data.rooms[k].roomStatus = 0;
                    }
                  }
                } else {
                  // 如果这两个表的状态一样为异常
                  if (equi_old[j].status == 1) {
                    all_data.rooms[k].warnEqu = add_arr(all_data.rooms[k].warnEqu, equi_old[j].equipmentId);
                  }
                }
              }
              if (!room_old[k].warnEqu ) {
                all_data.rooms[k].roomStatus = 0;
              }
            }
          }
          // 对于房间的要插入一条记录
          if(equi_new[i].status == 1 && equi_new[i].roomId == all_data.roomId){
            status = false;
            for(k = 0; k < all_data.historys.length; k++){
              if(all_data.historys[k].recordTime == equi_new[i].recordTime){
                status = true;
                new_obj = {
                  "roomId": equi_new[i].roomId,
                  "equipmentId": equi_new[i].equipmentId,
                  "equipmentName": equi_new[i].equipmentName,
                  "status": equi_new[i].status,
                  "pm1_0": equi_new[i].pm1_0,
                  "pm2_5": equi_new[i].pm2_5,
                  "pm10": equi_new[i].pm10,
                  "recordTime": equi_new[i].recordTime
                };

                all_data.historys[0].records.unshift(new_obj);
                break;
              }
            }
            if(status === false){
              new_obj = {
                "recordTime": equi_new[i].recordTime,
                "records": [
                  {
                    "roomId": equi_new[i].roomId,
                    "equipmentId": equi_new[i].equipmentId,
                    "equipmentName": equi_new[i].equipmentName,
                    "status": equi_new[i].status,
                    "pm1_0": equi_new[i].pm1_0,
                    "pm2_5": equi_new[i].pm2_5,
                    "pm10": equi_new[i].pm10,
                    "recordTime": equi_new[i].recordTime
                  }
                ]
              };
              all_data.historys.unshift(new_obj);
            }
          }
        }
      }
    }
  }

  function add_arr(arr, ele) {
    if (typeof arr == 'object') {
      if (arr.indexOf(ele) == -1) {
        arr.push(ele);
      }
    } else {
      arr = [ele];
    }
    return arr;
  }

  function push_arr(arr, ele) {
    if (arr && arr.length > 0) {
      if (arr.indexOf(ele) !== -1) {
        arr.splice(arr.indexOf(ele), 1);
      } else {
        console.log('这个设备原来正常，现在也正常');
      }
      return arr.length;
    } else {
      console.log('这个数组不存在或者长度为0');
    }
  }

  var open = function(e) {
    console.log('成功打开');
    var str = window.location.href;
    if (str.indexOf('device') !== -1) {
      console.log('发送设备' + 'see_equipment' + tea_id);
      socket.send('see_equipment' + tea_id);
    } else if (str.indexOf('histroy') !== -1) {
      console.log('发送历史记录请求see_history/' + tea_id)
    }
  }
  var error_event = function(e) {
    console.log('连接失败');
    alert('连接错误，请检查网络是否发生错误。');
  }
  var num = 0;
  var close = function(e) {
    console.log('连接关闭');
    alert('连接错误，请检查网络是否发生错误。');
  }
  socket.onopen = open;
  socket.onerror = error_event;
  socket.onclose = close;
  socket.onmessage = handler;
  var all_data = {
    // 这里之后要求改为一个空对象
    tea: {
      userId: 1,
      userName: '洋溢'
    },
    rooms: [{
      roomId: 1,
      roomName: '房间1',
      roomStatus: 0
    }, {
      roomId: 1,
      roomName: '房间2',
      roomStatus: 1
    }],
    equipments: [],
    historys: []
  };
}else{
  /** PC端所需的东西 **/
  var globals = {
    renderStr: function(type,user_name, user_id){
      var str = '';
      if(type === 'create'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_name_text lg_ip" placeholder="请输入老师名字"/>' +
          '<button type="text" class="send_add_tea bn bn_big">确认</button>' +
          '</div>';
      }else if(type === 'updata'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_name_text lg_ip" placeholder="请输入老师名字" value="'+ user_name +'"/>' +
          '<button type="text" class="send_add_tea bn bn_big">确认</button>' +
          '</div>';
      }else if(type === 'add_tea_tele'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_name_text lg_ip" placeholder="请输入添加的老师号码" value=""/>' +
          '<button type="text" class="send_add_tea bn bn_big">确认</button>' +
          '</div>';
      }else if(type === 'update_tea_tele'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_name_text lg_ip" placeholder="请输入修改的老师号码" value="' + user_name + '"/>' +
          '<button type="text" class="send_add_tea bn bn_big">确认</button>' +
          '</div>';
      }else if(type === 'add_tea_room'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_name_text lg_ip" placeholder="请输入添加的房间名字" value=""/>' +
          '<button type="text" class="send_add_tea bn bn_big">确认</button>' +
          '</div>';
      }else if(type === 'update_tea_room'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_name_text lg_ip" placeholder="请输入添加的房间名字" value="' + user_name + '"/>' +
          '<button type="text" class="send_add_tea bn bn_big">确认</button>' +
          '</div>';
      }else if(type === 'add_tea_equi'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_id_text lg_ip" placeholder="请输入添加的设备编号" value=""/>' +
          '<input type="text" class="tea_name_text lg_ip" style="margin-top: 10px;" placeholder="请输入添加的设备名字" value=""/>' +
          '<button type="text" class="send_add_tea  bn bn_big">确认</button>' +
          '</div>';
      }else if(type === 'update_tea_equi'){
        str = '<div class="prop_area">' +
          '<input type="text" class="tea_id_text lg_ip" placeholder="请输入添加的设备编号" value="' +  user_id + '"/>' +
          '<input type="text" class="tea_name_text lg_ip" style="margin-top: 10px;" placeholder="请输入添加的设备名字" value="' + user_name + '"/>' +
          '<button type="text" class="send_add_tea  bn bn_big">确认</button>' +
          '</div>';
      }
      return str;
    },
    updata_teacher: function(type, user_id, user_name){
      // 1. 渲染遮罩层；
      var parent = globals.render(),
        div = document.body.querySelector('.prop_container');
      var str = globals.renderStr(type, user_name);

      // 插入
      div.innerHTML = str;
      // 添加绑定事件
      document.body.querySelector('.close_prop').addEventListener('click', function(e){
        parent.parentNode.removeChild(parent);
      });
      document.body.querySelector('.send_add_tea').addEventListener('click', function(e){
        var value = document.body.querySelector('.tea_name_text');
        if(value){
          value = value.value;
          Ajax({
            url: '/smoke/teacher',
            type: type==='create'?'post':'put',
            data: {
              userId: user_id,
              userName : value
            },
            success: function(data){
              printf('158行：添加老师按钮添加data:' + data);
              if(type === "create"){
                  // 这是添加
                  pc_data.teachers.push(data);
                }else if(data.status == 1){
                  // 这是修改
                  for(var i = 0; i < pc_data.teachers.length; i++){
                    if(pc_data.teachers[i].userId === user_id){
                      pc_data.teachers[i].userName = value;
                      break;
                    }
                  }
                }else{
                  if(type==='create'){
                    alert('添加老师失败');
                  }else{
                    alert('修改老师失败');
                  }
                }
                // 触发按钮关闭事件；
                var btn = document.body.querySelector('.close_prop');
                var event = document.createEvent("MouseEvents");
                event.initMouseEvent('click', true, true, document.defaultView, 0,0,0,0,0,false, false, false, false, 0, null);
                btn.dispatchEvent(event);
              
            },
            error: function(){
              if(type==='create'){
                alert('添加老师失败');
              }else{
                alert('修改老师失败');
              }
            }
          })
        }
      });
    },
    render: function(){
      var div;
      if(document.body.querySelector('.prop')){
        div = document.body.querySelector('.prop');
      }else{
        div = document.createElement('div');
        div.className = 'prop';
        div.innerHTML = '<div class="prop_header"><span class="close_prop">X</span></div><div class="prop_container"></div>';
        document.body.appendChild(div);
      }
      return div;
    }
  }
  var pc_data = {
    teachers: [],
    temp: {
      phones: [{
        userId: 0,
        userPhone: ''
      }]
    }
  };

}
