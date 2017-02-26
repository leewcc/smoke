var msg = [
{
    url: '/smoke/teacher',
    data: {
          "status": 1,   //失败的话是0
          "message": "添加老师成功"
        }
},{
  url : '/smoke/phone',
  data: {
    "status": 1,
    "message": "电话格式有问题"
  }
},{
    url: '/user/1/roomlist',
    data: {
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
},{
    url: '/room/5/equipmentlist',
     data:{
      "data": [
        {
          "equipmentId": 31421,
          "equipmentName": "房间的新设5",
          "roomId": 5,
          "currentStatus": null
        }
      ],
      "success": true
    }
},{
    url: '/room/7/equipmentlist',
     data:{
      "data": [
        {
          "equipmentId": 31422,
          "equipmentName": "房间的新设7",
          "roomId": 7,
          "currentStatus": null
        }
      ],
      "success": true
    }
},{
    url: '/smoke/teachersInfo',
    data: [
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
      },
      {
        "userId": 14,
        "userName": "jimmyya",
        "rooms": null,
        "phones": []
      },
      {
        "userId": 14,
        "userName": "jimmyya",
        "rooms": null,
        "phones": []
      },
      {
        "userId": 14,
        "userName": "jimmyya",
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
},
{
    url: '/teacher/list',
    data: {
        "data": [
        	{ 
        		"userId": 1, 
        		"userName": "陈俊铭1", 
        		"rooms": null, 
        		"phones": null 
        	},
        	{ 
        		"userId": 2, 
        		"userName": "陈俊铭2", 
        		"rooms": null, 
        		"phones": null 
        	},
        	{ 
        		"userId": 3, 
        		"userName": "陈俊铭3", 
        		"rooms": null, 
        		"phones": null 
        	},
        	{ 
        		"userId": 4, 
        		"userName": "陈俊铭4", 
        		"rooms": null, 
        		"phones": null 
        	},
        	{ 
        		"userId": 5, 
        		"userName": "陈俊铭5", 
        		"rooms": null, 
        		"phones": null 
        	},
        	{ 
        		"userId": 6, 
        		"userName": "陈俊铭6", 
        		"rooms": null, 
        		"phones": null 
        	}
        ],
        "success": true
    }
}, {
    url: '/login',
    data: {
        status: 1,
        message: '登录成功'
    }
}, {
    url: 'see_room',
    data: {
        statusCode: 'SUCCESS',
        rooms: [{
            roomId: 1,
            roomName: '房间1',
            roomStatus: 0
        }, {
            roomId: 1,
            roomName: '房间2',
            roomStatus: 1
        }]
    }
}, {
    url: 'see_equipment/1',
    data: {
        statusCode: "SUCCESS",
        equipments: [{
            roomId: 1,
            equipmentId: 0,
            status: 0,
            PM1_0: 1,
            PM2_5: 1,
            PM10: 1,
            recordTime: "2016/2/2 10:23"
        }, {
            roomId: 1,
            equipmentId: 0,
            status: 0,
            PM1_0: 1,
            PM2_5: 1,
            PM10: 1,
            recordTime: "2016/2/2 10:23"
        }, {
            roomId: 1,
            equipmentId: 0,
            status: 0,
            PM1_0: 1,
            PM2_5: 1,
            PM10: 1,
            recordTime: "2016/2/2 10:23"
        }]
    }
}, {
    url: '/see_history/1',
    data: {
        choice: 3,
        teaId: 1,
        statusCode: "SUCCESS",
        historys: [{
            recordTime: "2016/2/2 10:23",
            record: [{
                roomId: 1,
                equipmentId: 1,
                status: 0,
                PM1_0: 1,
                PM2_5: 1,
                PM10: 1,
                recordTime: "2016/2/2 10:23"
            }]
        }]
    }
}];



for (var i = 0; i < msg.length; i++) {
    Mock.mock(msg[i].url, msg[i].data);
}
