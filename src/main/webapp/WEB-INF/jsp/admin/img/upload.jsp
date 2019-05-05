<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<div class="layui-body">
    <div id="back-container">
        <div class="container-content-left">
                <div class="layui-upload">
                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                        <legend>上传图片</legend>
                    </fieldset>
                    <div class="layui-upload">
                        <button type="button" class="layui-btn layui-btn-normal" id="btn-select">选择多文件</button>
                        <div class="layui-upload-list">
                            <table class="layui-table">
                                <thead>
                                    <tr>
                                        <th>文件名</th>
                                        <th>大小</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="imgList"></tbody>
                            </table>
                        </div>
                        <button type="button" class="layui-btn" id="upload">开始上传</button>
                    </div>
                </div>
            </div>
        <div class="container-content-right">
            <div class="layui-tab system-info">
                <div class="header">
                    <span>信息</span>
                    <ul class="circles fr layui-tab-title">
                        <li class="circle layui-this"></li>
                        <li class="circle"></li>
                        <li class="circle"></li>
                    </ul>
                </div>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <table class="layui-table">
                            <thead class="layui-table-header" align="center">
                            <tr>
                                <td colspan="2">当前版本信息</td>
                            </tr>
                            </thead>
                            <tbody class="layui-table-body">
                            <tr>
                                <td>当前版本</td>
                                <td>1.0</td>
                            </tr>
                            <tr>
                                <td>基于框架</td>
                                <td>layui</td>
                            </tr>
                            <tr>
                                <td>主要特色</td>
                                <td>清爽/极简</td>
                            </tr>
                            <tr>
                                <td>博主</td>
                                <td>BruseLing</td>
                            </tr>
                            <tr>
                                <td>源码</td>
                                <td>
                                    <a href="https://github.com/ljtnono/myblog" target="_blank">点击这里进入Github查看</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="layui-tab-item">
                    </div>
                    <div class="layui-tab-item">内容3</div>
                </div>
            </div>
            <div class="link-blog-container">
                <div class="header">
                    <span>查看主页</span>
                </div>
                <div class="link-blog-content">
                    <a class="layui-btn btn-link-blog" href="http://www.ljtnono.cn/myblog" target="_blank">点击查看主页</a>
                </div>
            </div>
            <div class="message-container">
                <div class="header">
                    <span>作者寄语</span>
                    <i class="fa  fa-exclamation-circle fr exclamation"></i>
                </div>
                <div class="message-content">
                    <p>
                        无意间看到大神崔庆才的博客系统，萌生了自己独立开发一个博客系统的想法，于是说做就做，就有了这个个人博客系统。起初做的还算有激情，后来发现如果是一个人开发的话，那么自己不但需要做UI，做后台，还需要自己设计架构，设计数据库，需要学习的东西太多了，中间也想过放弃，但是还是坚持下来了。本来自己数学功底不好，导致算法功底也不好，一些难的功能自己也不能实现。只能做做简单的增删改查。希望这个博客项目能够锻炼自己的能力吧。
                    </p>
                    <p>
                        一句话勉励自己
                    </p>
                    <p>
                        努力，奋斗！-----周星驰
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/admin/img/upload.min.js"></script>