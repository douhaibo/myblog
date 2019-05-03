# myblog
这是一个使用java作为后端的博客系统，使用的是editor.md插件作为markdown文本编辑器<br>
本博客部分内容参考温志怀博客和思欲主题，详情请移步  [温志怀博客](http://www.wenzhihuai.com) | [思欲主题](https://yusi123.com/)<br>
网站的站点：[www.ljtnono.cn/myblog](www.ljtnono.cn/myblog)<br>
下面是博客的首页<br>

![博客首页](https://www.ljtnono.cn/group1/M00/00/00/rBUAC1zKoxGACeKiAAN_4hNttpw288.png)


# 博客的技术选型 
[![GitHub stars](https://img.shields.io/github/stars/ljtnono/myblog.svg)](https://github.com/ljtnono/myblog/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/ljtnono/myblog.svg)](https://github.com/ljtnono/myblog/network)
[![GitHub issues](https://img.shields.io/github/issues/ljtnono/myblog.svg)](https://github.com/ljtnono/imyblog/ssues)


**Java**：Spring、Spring MVC、Mybatis
**前端**：Bootstrap、Jquery、Echarts、WowSlider（图片切换）、layui 
**图片存储**：fastdfs分布式图片框架
**缓存**：Redis
**数据库**：MySQL  
**部署**：Tomcat、Nginx、阿里云服务器


# 更新日志
* 2018/11/1 <br>
今天第一天开始弄这个个人博客网站，感觉好激动，每次做一个新的东西都会有很多想法<br>
希望这个网站不要做到一半就放弃，也希望自己能够做好这个网站。<br>
更新内容：<br>
1、创建了首页，虽然有点难看，但是好歹可以访问了。<br>
2、搭建了SSM环境，后续将会引入shiro，在多写出来几个页面出来之前。<br>

* 2018/11/3 <br>
更新内容：<br>
1、分离出来了header和footer页面<br>
2、修复hot5页面head部分不能垂直居中的问题<br>

* 2018/11/11 <br>
这个星期一直在学习安卓相关的知识，没有太多时间去做这个了，尽管如此，还是抽出点时间来更新这个项目<br>
增加了一个页面的内容,具体更新内容如下<br>
更新内容：<br>
1、添加一个技术文章页面<br>
2、引入font-awesome字体图标，减少了网页图片<br>
3、分离出来文章列表部分的样式形成单独的文件<br>
4、将header和footer jsp中的html骨架去掉，只剩下内容部分<br>
5、修复了side部分没有指定固定高而导致的内容错误的BUG<br>
后续尽力保持一个星期更新一个页面的速度，等页面写完了，将开始着手写后台代码。<br>

* 2018/11/14<br>
由于最近两门课节课了，所以这个星期三就更新了，如果可能的话那么这个星期两次更新<br>
更新内容：<br>
1、side部分的如果只写height:100%那么会比内容撑起来的高度要高一点，初步估计是tomcat解析问题，今后注意下<br>
2、新增文章详情页面<br>
3、使用了Editor.md来显示文章内容，修复了.tag 类名样式污染BUG<br>
4、由于side部分的样式重复，分离出来一个articles_side.css样式<br>
5、接入畅言评论系统感觉还不错哦<br>


* 2019/3/9 <br>
前面4个月一直在学习其他的东西，一直没有时间去跟新这个博客，现在又想起来自己还有一个博客系统没有写完，于是继续
撸起袖子加油干<br>
更新内容：<br>
1、整合了fastdfs分布式文件系统<br>
2、后台添加了图片管理功能,实现了图片的添加，删除，查询。<br>
3、添加一个模块显示系统信息<br> 

* 2019/3/10 <br>
今天还是写后台管理图片的部分<br>
更新内容：<br>
1、添加了图片的条件查询 <br>
2、实现图片的批量删除 <br>

* 2019/3/11 <br>
今天还是写了一个aboutme页面<br>
更新内容：<br>
1、个人信息展示 <br>
2、个人技能柱状图<br>
3、更新时间轴<br>

* 2019/3/12 <br>
更新内容：<br>
1、根据id、title查询图片<br>
2、曲线图监控服务器的硬件信息<br>

* 2019/3/13 <br>
更新内容：<br>
1、更新了icon图片<br>

测试分支


**持续更新---**
