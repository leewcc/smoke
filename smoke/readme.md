#烟雾项目

##简介：
这个项目，主要包括二个端，分别是管理端、用户端。

##管理端：
主要是我们可以添加人员，在人员的名义下可以添加房间，每个房间可以绑定多个电话号码，可以绑定多个设备。
每个设备都有自己的初始状态和编号。


##用户端：
用户端登录主要是使用唯一的二维码，二维码由外部系统生成。
登录后，可以看到自己名以下的所有实验室
每个实验室点进去都可以看到具体的设备情况
每个设备都有具体的详情
在技术上，一方面使用tcp socket去接收数据，另一方面使用websocket去和页面交互。

##日程安排
- 2016-11-26
后台：完成框架的搭建和数据库设计

- 2016-11-27
后台：完成tcp模块，和websocket模块

- 2016-11-28
后台：完成dao层编写
前端：完成登录页面逻辑编写。

- 2016-11-29
后台：完成登录页面

- 2016-11-30
后台：完成添加信息页面

- 2016-12-1
后台：完成用户端的显示房间界面

- 2016-12-2
后台：完成用户端的显示设备页面


# 前端页面
打开前端页面需要安装node环境在webpage所属文件夹下打开git bash 输入npm run dev