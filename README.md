# socket-demo

**项目说明** 
- socket-demo是一个socket服务端客户端交互数据的小项目
<br> 

**项目结构**

```
socket-parent
├─socket-core 核心模块
├─socket-server socket服务端
└─socket-client socket客户端
```
<br> 

```
socket-server
├─common 公共模块
│  ├─config 系统配置信息
│  ├─constants 静态变量
│  ├─properties 自定义配置
│  ├─utils 工具类
│  └─vo 项目整理返回对象
│ 
├─runner socket服务端启动
│ 
├─socket socket模块
│  ├─domain socket交互信息
│  ├─handle socket信息处理
│  ├─service socket推送服务
│  ├─vo 项目整理返回对象
│  └─vo 项目整理返回对象
│ 
└─SocketServerApplication 项目启动类
```
<br> 