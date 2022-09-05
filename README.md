# Bilibili-API

实现部分b站接口，用来为机器人提供一个可用的java实现 api。  
**所有的api在每个对应的接口根包下有个最为简略的文档，他指引你使用正确的对象与方法**  
该api功能包含以下内容但是不限于以下内容

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

### 日志

代码中只有直播TCP部分代码与视频下载等部分代码使用了日志，其他的不使用日志

#### 解决日志绑定冲突

##### 问题描述：

使用 **Releases** 中的jar 再导入项目后，如果项目内有日志的绑定，那么他们会发生冲突

##### 问题原因：

jar 中导入了日志依赖与你项目里面的日志依赖发生了冲突。  
但是 Releases 中打包的jar，不能使用 exclusions 去除，因为他将所有的依赖都整合进了jar中。  
需要手动打包一份没有依赖的jar，或者手动删除冲突依赖

##### 解决方法：

1. 重新使用maven打包项目，打包后的jar不应该包含依赖的jar
   但是请将 BiliCore 所需的依赖导入你所在的项目里面

```xml

<dependencies>

    <dependency>
        <!--maven 导入本地jar-->
        <groupId>io.github.zimoyin</groupId>
        <artifactId>BilibiliAPI</artifactId>
        <version>0.3</version>
        <scope>system</scope>
        <!-- 本地jar 路径-->
        <systemPath>${project.basedir}/lib/BilibiliAPI-0.3.jar</systemPath>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.openjfx/javafx-controls -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>18.0.2</version>
    </dependency>


    <dependency>
        <groupId>io.vertx</groupId>
        <artifactId>vertx-core</artifactId>
        <version>4.3.2</version>
    </dependency>
    
    <!--logo二维码 工具-->
    <dependency>
        <groupId>com.github.binarywang</groupId>
        <artifactId>qrcode-utils</artifactId>
        <version>1.2</version>
    </dependency>
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>core</artifactId>
        <version>3.3.0</version>
    </dependency>
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>javase</artifactId>
        <version>3.3.0</version>
    </dependency>
    <dependency>
        <groupId>net.coobird</groupId>
        <artifactId>thumbnailator</artifactId>
        <version>0.4.8</version>
    </dependency>


    <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
    <dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpclient</artifactId>
        <version>4.5.13</version>
    </dependency>
    <dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpmime</artifactId>
        <version>4.5</version>
    </dependency>


    <!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.14.3</version>
    </dependency>
    
    <!-- https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java -->
    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>3.19.3</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.60</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-compress</artifactId>
        <version>1.20</version>
    </dependency>
</dependencies>
```

2. 暴力删除jar中的依赖

----
大部分的接口来源于项目： https://github.com/SocialSisterYi/bilibili-API-collect  
开发阶段，所有代码以及结构都会发生不平滑变动  
本人初学java，代码质量堪忧，望包容  