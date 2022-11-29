# Bilibili-API

实现部分b站接口，用来为机器人提供一个可用的java实现 api。  
**所有的api在每个对应的接口根包下有个最为简略的文档，他指引你使用正确的对象与方法**  
该api功能包含以下内容但是不限于以下内容(下列api的具体内容请见每个包下的文档)

* 视频API
* 用户部分API
* 获取登录Cookie
    * 检查昵称是否可注册(实现)
* 直播API
* 评论区API
* 搜索API
* 私信API
* 收藏夹API
* 专栏API
* 合集API
* 歌单API
  **注意：一定要看源码的pojo类的字段，否则你不会用的**
* 关于视频下载： 对于视频下载只提供了简陋的下载工具，如有需求请定制

### 依赖对应

阐述每层代码对应的不共用依赖

| 代码层                         | 依赖                |
| :----------------------------- | :------------------ |
| github.zimoyin.core.barrage    | com.google.protobuf |
| github.zimoyin.core.collection | org.jsoup           |
| github.zimoyin.core.column     | org.jsoup           |
| github.zimoyin.core.login      | com.google.zxing    |
| github.zimoyin.core.login      | org.openjfx         |
| github.zimoyin.core.live       | io.vertx            |

### 本地导入jar
**请自行打包为 jar**
```xml

<dependency>
    <!--maven 导入本地jar-->
    <groupId>io.github.zimoyin</groupId>
    <artifactId>BilibiliAPI</artifactId>
    <version>0.3</version>
    <scope>system</scope>
    <!-- 本地jar 路径-->
    <systemPath>${project.basedir}/lib/BilibiliAPI-0.3.jar</systemPath>
</dependency>
```

----
大部分的接口来源于项目： https://github.com/SocialSisterYi/bilibili-API-collect  
**本人初学java，代码质量堪忧，望包容**  