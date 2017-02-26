#管理端的接口
#管理端的接口

[TOC]

##获得所有的老师的id号，姓名，电话号
http://localhost:8080/smoke/teachersInfo  get

```
[
  {
    "userId": 1,
    "userName": "陈俊铭",
    "rooms": null,
    "phones": [
      {
        "userId": 1,
        "userPhone": "123456"
      },
      {
        "userId": 1,
        "userPhone": "456123"
      }
    ]
  },
  {
    "userId": 9,
    "userName": "吴小聪",
    "rooms": null,
    "phones": []
  },
  {
    "userId": 12,
    "userName": "张诗婷",
    "rooms": null,
    "phones": []
  },
  {
    "userId": 13,
    "userName": "chen",
    "rooms": null,
    "phones": []
  },
  {
    "userId": 14,
    "userName": "jimmyya",
    "rooms": null,
    "phones": []
  }
]
```

##获得所有老师的id号，名字
http://localhost:8080/teacher/list get

```
{
  "data": [
    {
      "userId": 1,
      "userName": "陈俊铭",
      "rooms": null,
      "phones": null
    },
    {
      "userId": 9,
      "userName": "吴小聪",
      "rooms": null,
      "phones": null
    },
    {
      "userId": 12,
      "userName": "张诗婷",
      "rooms": null,
      "phones": null
    },
    {
      "userId": 13,
      "userName": "chen",
      "rooms": null,
      "phones": null
    },
    {
      "userId": 14,
      "userName": "jimmyya",
      "rooms": null,
      "phones": null
    }
  ],
  "success": true
}
```

##添加一个老师
http://localhost:8080/smoke/teacher post
发送

```
{"userName":"温天信"}
```

收到

```
{
  "status": 1,   //失败的话是0
  "message": "添加老师成功"
}
```


##删除一个老师
http://localhost:8080/smoke/teacher/15 delete
收到

```
{
  "status": 1, //失败的话是0
  "message": "删除老师成功"
}
```


##修改一个老师
http://localhost:8080/smoke/teacher put
发送

```
{"userName": "吴小聪","userId":"13"}
```
收到
```
{
  "status": 1,   //失败的话是0
  "message": "修改信息成功"
}
```



##新增一个设备
http://localhost:8080/smoke/equipment post

发送
```
{"equipmentId":"31421","equipmentName":"房间的设备","roomId":"5"}
```
收到

```
{
  "status": 1,
  "message": "添加设成功"
}
```

##修改设备的名字
http://localhost:8080/smoke/equipment put
发送
```
{"equipmentId":"31421","equipmentName":"房间的新设备"}
```
收到

```
{
  "status": 1,
  "message": "修改设成功"
}
```

##删除一个设备
http://localhost:8080/smoke/equipment/1 delete
收到
```
{
  "status": 1, //失败的话是0
  "message": "删除设备成功"
}
```

##获得房间下的所有设备(OK)
http://localhost:8080/room/5/equipmentlist get
收到

```
{
  "data": [
    {
      "equipmentId": 31421,
      "equipmentName": "房间的新设备",
      "roomId": 5,
      "currentStatus": null
    }
  ],
  "success": true
}
```

##新增一个电话号码
http://localhost:8080/smoke/phone post
1. 发送

```
{"userId":13,"userPhone":"123456"}
```
收到

```
{
  "status": 1,
  "message": "电话格式有问题"
}
```

2. 发送

```
{"userId":13,"userPhone":"15914370252"}
```
收到

```
{
  "status": 1,
  "message": "新增电话成功"
}
```

##删一个电话
http://localhost:8080/smoke/phone/1/123456 delete
收到

```
{
  "status": 1,
  "message": "删除电话成功"
}
```

##修改一个电话
http://localhost:8080/smoke/phone put
发送

```
{"userId":"1","userPhone":"123456"}
```
收到

```
{
  "status": 1,
  "message": "修改电话成功"
}
```


##获得某个用户所有的电话
http://localhost:8080/user/1/phonelist get

收到
```
{"data":[{"userId":1,"userPhone":"123456"}],"success":true}
```


##获得某个用户名下的所有房间号
http://localhost:8080/user/1/roomlist get

```
{
  "data": [
    {
      "roomId": 5,
      "roomName": "我的一号房间",
      "status": 0,
      "teacher": null,
      "equipments": null,
      "historys": null,
      "warningEquipment": null
    },
    {
      "roomId": 7,
      "roomName": "我的二号房间",
      "status": 0,
      "teacher": null,
      "equipments": null,
      "historys": null,
      "warningEquipment": null
    }
  ],
  "success": true
}
```

##新增一个房间
http://localhost:8080/smoke/room post
发送

```
{"roomName":"俊铭的","userId":"1"}
```
收到

```
{
  "data": {
    "userId": 1,
    "roomId": 12,
    "roomName": "俊铭的",
    "status": 0,
    "teacher": {
      "userId": 0,
      "userName": null,
      "rooms": null,
      "phones": null
    },
    "equipments": null,
    "historys": null,
    "warningEquipment": null
  },
  "success": true
}
```

##删除一个房间
http://localhost:8080/smoke/room/12 delete

```
{
  "status": 1,
  "message": "删除房间成功"
}
```

##修改一个房间
http://localhost:8080/smoke/room put
发送

```
{"roomName":"俊铭的","roomId":"5"}
```

收到

```
{
  "status": 1,
  "message": "修改房间成功"
}
```