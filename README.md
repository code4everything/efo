### 关于项目

- 项目名称 `efo` 是 `Easy File Online` 的缩写，字面意思就是让您轻松实现线上文件管理

- 本系统具有文件分享的功能，权限控制和自定义配置都很强大（可能还不完善）

- 系统后端框架有Spring Boot，Spring， SpringMVC，MyBatis; 前段框架有Bootstrap，Jquery, Layer, Vue。项目完全纯注解，零XML配置。

> 第一次运行系统，请先运行 [SQL代码](/mysql/efo.sql) , 并登陆系统修改用户 `system` （默认密码 `123456`）的密码

### 系统部分截图（背景图片可通过配置设置）

- 登录页面（包含登录、注册、密码重置），路径 `/signin` 

	![登录页面](http://oq4pzgtcb.bkt.clouddn.com/git/efo/signin.png)
	
- 资源首页，路径 `/index` 

	![登录页面](http://oq4pzgtcb.bkt.clouddn.com/git/efo/index.png)
	
- 上传页面，路径 `/upload` 

	![登录页面](http://oq4pzgtcb.bkt.clouddn.com/git/efo/upload.png)
	
- 管理员管理页面，路径 `/admin` 

	![登录页面](http://oq4pzgtcb.bkt.clouddn.com/git/efo/admin.png)
	
- 远程文件管理（管理服务器端所有文件，只有系统用户才能进入此页面），路径 `/filemanager` 

	![登录页面](http://oq4pzgtcb.bkt.clouddn.com/git/efo/filemanager.png)
	
	> 此功能基于 `angular-filamanager` 实现，其中上传部分我弄的还不是很明白，用的很笨的方法实现了上传（但是最多只能上传11个文件，可修改代码，自行设置）
	
	> 有知道怎么弄的大神，望告知啊。
	
	> [angular-filamanager](https://github.com/joni2back/angular-filemanager)
	
**最后，如果大家有什么好的建议欢迎提出哦，觉得可以的话，给个star呗**