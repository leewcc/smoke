#项目进度汇报


## 2016/11/29
后台伟淙：
1. 添加了 SmokeState 类，修改了部分 pojo 类（Teacher、User、Equipment)
2. 添加了客户端与服务端进行 websocket 连接的通信细节
3. 添加了服务器监听阿里云服务器的监听类，正在开始编写数据解析与处理类


前端诗婷：完成前端项目的基本配置。计划明天动工跳转页面的样式和逻辑。

## 2016/11/30
后台伟淙：
1. 确认了与前端的通信方式，并提交了接口文档
2. 编写完了 websocket 端通信的命令实现，只差将数据进行 json 的转化
3. 编写了报文解析与处理任务类，该类还差将报文的信息同步


全组事项：商量原型和理清思路。

前端诗婷：什么都没干，这个混蛋。

后台俊铭：定义了数据库接口，仰望楼上。

## 2016/12/01

后台伟淙：
1. 编写了 json 数据转化格式类
2. 编写了关于 dao 层的接口
3. 编写了项目初始化监听器，该类用于初始化内存共享对象，和打开数据监听

后台俊铭：
1. 编写了dao层 、service层、controller层接口
2. 配置了cookie
3. 修改了rsa算法
4. 配置了日志


## 2016/12/02
后台伟淙：
1. 完善了与前端通信的定义的四个接口的数据转化
2. 完善了数据解析类的报文处理工作（剩余警告报文未添加插入数据库操作和发送信息通知用户操作）
3. 下一步是编写一个报文发送器，来进行数据模拟，与前端进行通信交互测试

前端诗婷：
1. 将文件移入WEB-INF中,重新配置了环境
2. 处理完用户登录界面的逻辑，还没修改url路径

后台俊铭:
1. 增加了短信接口

## 2016/12/03
后台铭：
1. 短信模板通过验证，并成功使用


## 2016/12/12

前端诗婷：
1. 修改了登录页面的样式；
2. 剩余测试自动推送和测试移动端样式性能