# User 接口

该接口主要为用户信息获取

### 类功能

#### 用户信息

```java
package github.zimoyin.bili.user.userinfo;
```
该包下有用户信息的接口工具

##### 用户信息

```java
package github.zimoyin.bili.user.userinfo;
return github.zimoyin.core.user.pojo.user.UserRootBean;
```
该类实现了获取user信息接口，可用获取用户的基本信息，以一个pojo对象返回  
部分功能需要cookie
功能概述列表(功能概述列表依照pojo排列):  
* Data
> 用户mid  
> 用户名  
> 头像URL  
> 签名  
> 等级  
> 封禁状态  
> 认证信息  
> 直播间信息


##### 用户卡片

```java
package github.zimoyin.bili.user.userinfo;
return github.zimoyin.core.user.pojo.usercare.UserCareRootBean;
```
部分功能需要cookie  
功能概述列表(功能概述列表依照pojo排列):
* Data
> 用户的跟随者数量（粉丝）  
> Card:  
> > 用户名  
> > 用户性别  
> > 用户头像链接  
> > 关注数
> > 粉丝数
> > 等级
> > 签名

##### 我的信息

```java
package github.zimoyin.bili.user.userinfo;
return github.zimoyin.core.user.pojo.my.UserMyRootBean;
```
注意需要cookie  
功能概述列表(功能概述列表依照pojo排列):
* Data
> 用户mid  
> 用户名  
> 头像URL  
> 签名  
> 等级
> 认证信息  
> 直播间信息

#### 用户作品与搜索用户
```java
github.zimoyin.core.user.up
```

##### 用户作品

```java
package org.zimoyin.core.user.up;
return github.zimoyin.core.user.pojo.works.UserWorksRootBean;
```
功能概述列表(功能概述列表依照pojo排列):
* Data
> 作品：  
> > 分区
> > 作品列表

##### 搜索用户

```java
package org.zimoyin.core.user.up;
return github.zimoyin.core.user.pojo.search.SearchUserTableRootBean
```
功能概述  
> 搜索用户
> 搜索指定用户并提供了一个方法

#### 状态
以下功能不是主流就不书写了
##### up状态
github.zimoyin.core.user.state.AlbumStatus
##### 相簿状态
github.zimoyin.core.user.state.AlbumStatus
##### 订阅&投稿状态数
github.zimoyin.core.user.state.ContributionStat
##### pojo类所在包
github.zimoyin.core.user.pojo.state

#### 用户关系（未写）
获取用户的粉丝信息（前250个）：github.zimoyin.core.user.userinfo.UserFans  
获取用户的关注信息：github.zimoyin.core.user.userinfo.UserFollow  
其他关注信息情况：未实现（以后按需求酌情实现）  
黑名单：未实现（以后按需求酌情实现）  
操作用户关系，如关注等：未实现（以后按需求酌情实现）  
关注分组：未实现（以后按需求酌情实现）  
#### 用户空间（未实现）
视频 、收藏 、订阅等  
以后按需求酌情实现  