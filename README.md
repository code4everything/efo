### 关于项目

- 项目名称 `efo` 是 `Easy File Online` 的缩写，字面意思就是让您轻松实现线上文件管理

- 本系统具有文件分享的功能，权限控制和自定义配置都很强大（可能还不完善）

- 系统后端框架有Spring Boot，Spring， SpringMVC，MyBatis; 前段框架有Bootstrap，Jquery, Layer, Vue。项目完全纯注解，零XML配置。

> 第一次运行系统，请先运行 [SQL代码](/mysql/efo.sql) , 并登陆系统修改用户 `system` （默认密码 `123456`）的密码

### 环境要求

- MySQL 5.7+

- JDK 1.8+

### 系统部分截图（背景图片可通过配置设置）

- 登录页面（包含登录、注册、密码重置），路径 `/signin` 

	![登录页面](http://towerpan.qiniu.segocat.com/git/efo/signin.png)
	
- 资源首页，路径 `/index` 

	![登录页面](http://towerpan.qiniu.segocat.com/git/efo/index.png)
	
- 上传页面，路径 `/upload` 

	![登录页面](http://towerpan.qiniu.segocat.com/git/efo/upload.png)
	
- 管理员管理页面，路径 `/admin` 

	![登录页面](http://towerpan.qiniu.segocat.com/git/efo/admin.png)
	
- 远程文件管理（管理服务器端所有文件，只有系统用户才能进入此页面），路径 `/filemanager` 

	![登录页面](http://towerpan.qiniu.segocat.com/git/efo/filemanager.png)
	
	> 此功能基于 [angular-filamanager](https://github.com/joni2back/angular-filemanager) 实现
	
**项目有不足的地方欢迎提出来哦，大家一起交流学习，觉得不错的话，Star来一个呗**
