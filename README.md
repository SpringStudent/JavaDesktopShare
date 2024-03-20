## desktop share using java

### step1

`将index.html中的http地址和websocket地址修改为你本地的DesktopServer服务启动地址`

### step2

`在DesktopClient.java中修改ffmpeg的路径，然后运行DesktopClient.java。`

### step3

`在桌面客户端打开菜单`

![step3](step3.png)
`进入对话框，然后输入您的桌面服务器URL，如 http://172.16.1.72:11111/receive?id=xxx 请注意，id不能包含中文或重复。`

![step3-1](step3-1.png)

`点击按钮然后等待连接成功`

![step3-2](step3-2.png)


### step4

`在您的浏览器中访问 http://{您的桌面服务器IP}:{您的桌面服务器端口}/index.html，然后选择您的客户端进行查看。`

![step4](step4.png)
