# Cookie
`github.zimoyin.core.cookie.Cookie` 为所有cookie的父类，同时也是他作为cookie参数在api系统中进行传递
### 如何获取Cookie
请通过 `github.zimoyin.core.login.Login` 接口内的默认方法来实现登录
### 如何持久化Cookie
1. Cookie类通过工具类内的方法，只需要调用静态方法`writeCookie` 或 对象方法 `writeCookieToJson` 即可  
2. cookie的保存路径请保存至`./cache/cookie/`目录下，这样 `GlobalCookie.java` 会在初始化时读取被持久化的cookie  
3. 请将持久化的cookie的文件后缀设置为 `.json`,否则 `GlobalCookie` 不会读取  
### 如何读取硬盘上的Cookie
Cookie类通过类内的方法，只需要调用静态方法`readCookie`即可  
### 全局Cookie
1. 全局Cookie会在 HttpClientUtils.java 中使用，如果不想请求携带全局Cookie，请把HttpClientUtils.java 内的 isGlobalCookie 设置为false  
2. 全局cookie会自动扫描 `./cache/cookie` 路径，读取Cookie文件。扫描位置可变，只需更改`Login.java 下的 cookiePath`即可