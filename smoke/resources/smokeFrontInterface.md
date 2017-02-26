# smoke 项目接口文档
## 建立连接
```
url:/websocket
request:{
　　userId:
}
response{
    choice: 0,
    teaId: 1,
    teacher:{
        userId : 1,
        userName: 'sss'
    },
	rooms:[
		{
			roomId:  1,
			roomName: 'sss',
			roomStatus: 0-正常 1-警告
        }
	]
}
```

## 查看房间状态
```
command: see_room

response:{
  choice : 1
  teaId : 
  statusCode: "SUCCESS","ERROR"
  
  rooms:[
		{
			roomId:
			roomName:
			roomStatus: 0-正常 1-警告
        }
	]"
}
```

## 查看房间设备状态
```
command：see_equipment/:roomId

response:{
    
    choice : 2
    teaId :
	statusCode: "SUCCESS" "ERROR"
	equipments:[
		{
		    roomId:
		    equipmentId:
		    status:
		    PM1_0:
		    PM2_5:
		    PM10:
		    recordTime:
		}
	]
}
```

## 查看房间历史记录
```
command：see_history/:roomId

response:{
choice:3
teaId:
statusCode: "SUCCESS" "ERROR"
historys:
	[
	{
		recordTime:
		record: [
			{
				roomId:
			    equipmentId:
			    status:
			    PM1_0:
			    PM2_5:
			    PM10:
			    recordTime:
			}
		]
	}
	]
}
```

## 主动推送
```
response:{
choice:4
teaId:
smokeStatus :
	[
		{
		    roomId:
		    equipmentId:
		    status:
		    PM1_0:
		    PM2_5:
		    PM10:
		    recordTime:
		}
	]
}
```


首先说明一下 我所有数据都是一两种方式发送的
详见dto包下的DataGram和Message
简单来说，如果只是简单的操作结果，那么我就会返回Message
{
	'status':1,//1 表示成功，0表示失败
	‘message’:'具体的信息'
}
如果是需要数据的操作，那么我将返回一个DataGram的包
例如：
[
{"roomId":1,"roomName":"123","status":0,"teacher":null,"equipments":null,"historys":null},{"roomId":2,"roomName":"456","status":0,"teacher":null,"equipments":null,"historys":null}],    //以上是一个数组对象，如果只是一个对象那么没有[]
    "success":true//操作的结果
    }

}
## 管理员
###登录结果
url:/login
{
	'status':1,
	'message':"登陆成功"
}
{
	'status':0,
	'message':"用户名或账号密码不正确"
}
{
	'status':0,
	'message':"服务器出错"
}


###url:/login post
{
    name:         //用户名
    password:     //密码
    code:         //验证码
}


##设备
###根据房间号绑定设备号和名字
url:/smoke/equipment post
{
	equipmentId:设备的编号
    equipmentName:设备的名字
    roomId:房间号
}

###根据设备id修改设备
url:/smoke/euqipment put
{
    equipmentId:
    equipmentName:
    roomId:
}

###根据设备id修改设备
url:/smoke/euqipment/{equipmentId} delete
equipmentId 要进行删除的设备号

###列出房间id下的所有设备
url:/room/{roomId}/equipmentlist
roomId 房间号
{
    {"data":
        [
        {"equipmentId":7,"equipmentName":"俊铭的房间","currentStatus":null,"room":null}],
        "success":true
    }
}

##手机号
###根据用户id新增一个他的电话号码
url:/smoke/phone post
{
      userId:
      userPhone:
}
###删除一个电话号码
url:/smoke/phone/{phoneId} delete
phoneId: 要进行删除的电话号

###修改一个电话号码
url:/smoke/phone put
{
      userId:
      userPhone:
}
###获得某个用户名下的所有电话清单
url:/user/{userId}/phonelist get
{
"data":
    [{"userId":1,"userPhone":123456},{"userId":1,"userPhone":456123}],
    "success":true
}



##房间
###根据用户id新增一个他名下的房间
url:/smoke/room post
{
    roomName:
    userId:
}
###根据房间号删除房间(OK)
url:/smoke/room/{roomId} delete
roomId 删除房间号

###修改房间名(OK)
url:/smoke/room put
{
    roomName:
}

###根据用户的id获得他名下的所有房间情况(OK)
url:/user/{userId}/roomlist get
{
    {"data":
        [{
            "roomId":1,
            "roomName":"123",
            "status":0,
            "teacher":null,
            "equipments":null,
            "historys":null
        },{
            "roomId":2,
            "roomName":"456",
            "status":0,
            "teacher":null,
            "equipments":null,
            "historys":null
        }],
    "success":true}

}

##老师
###获得所有的老师列表(OK)
url:/teacher/list get
{
"data":
    [{"userId":1,"userName":"陈俊铭","rooms":null,"phones":null}],
    "success":true
}

###新增一个有名字的老师(OK)
url:/smoke/teacher post
{
    userName:
}

###根据id删除一个老师(OK)
url:/smoke/teacher/{teacherId}  delete
teacherId　　老师的账号


###根据用户id去修改老师名字(OK)
url:/smoke/teacher put
{
    userId:
    userName:
}


###url: /smoke/teacher
返回老师信息展示页面



## url: /smoke/get_all get
response:
{
    data: [
        {
            userId: '用户名字',
            userName: '用户名字',
            userPhone: ['用户手机号','用户手机号','用户手机号'],
            rooms: [
                {
                    "roomId":1,
                    "roomName":"123",
                    "status":0, 
                    "teacher": '老师名字',
                    "equipments": [
                        {
                            equipmentId: 1
                            equipmentName: '设备名字'
                            roomId: 1
                        },
                        {
                            equipmentId: 1
                            equipmentName: '设备名字'
                            roomId: 1
                        }
                    ],
                    "historys":null
                },{
                    "roomId":2,
                    "roomName":"456",
                    "status":0,
                    "teacher":null,
                    "equipments":null,
                    "historys":null
                }
            ]
        },{
            userId: '用户名字',
            userName: '用户名字',
            userPhone: ['用户手机号','用户手机号','用户手机号'],
            rooms: [
                {
                    "roomId":1,
                    "roomName":"123",
                    "status":0, 
                    "teacher": '老师名字',
                    "equipments": [
                        {
                            equipmentId: 1
                            equipmentName: '设备名字'
                            roomId: 1
                        },
                        {
                            equipmentId: 1
                            equipmentName: '设备名字'
                            roomId: 1
                        }
                    ],
                    "historys":null
                }
                ,{
                    "roomId":2,
                    "roomName":"456",
                    "status":0,
                    "teacher":null,
                    "equipments":null,
                    "historys":null
                }
            ]
        }
    ],
    "status": 'SUCCESS'或'ERROR' 接受数据是否成功
}


###获得所有老师和他名下的所有电话

url:/smoke/teachersInfo
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
    }
]

