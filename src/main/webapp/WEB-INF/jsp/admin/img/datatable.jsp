<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<link rel="stylesheet" href="${basePath}css/admin/img/datatable.min.css">
<div class="layui-body">
    <div id="back-container">
        <div class="container-content-left">
            <div class="layui-card form-search-container">
                <form class="layui-form">
                    <div class="layui-form-item">
                        <label class="layui-form-label">查询图片</label>
                        <div class="layui-input-block form-search">
                            <input type="text" name="id"   placeholder="请输入ID"
                                   autocomplete="off" class="layui-input  fl">
                            <input type="text" name="title"   placeholder="请输入标题"
                                   autocomplete="off" class="layui-input  fl">
                            <button class="layui-btn" lay-submit lay-filter="btn-search">
                                <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                            </button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="datatable-container">
                <table id="datatable-img" lay-filter="datatable-img"></table>
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
            <%--跳转到首页按钮--%>
            <div class="link-blog-container">
                <div class="header">
                    <span>查看主页</span>
                </div>
                <div class="link-blog-content">
                    <a class="layui-btn btn-link-blog" href="http://www.ljtnono.cn/myblog" target="_blank">点击查看主页</a>
                </div>
            </div>
            <%--作者寄语--%>
            <div class="message-container">
                <div class="header">
                    <span>作者寄语</span>
                    <i class="fa  fa-exclamation-circle fr exclamation"></i>
                </div>
                <div class="message-content">
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        无意间看到大神崔庆才的博客系统，萌生了自己独立开发一个博客系统的想法，于是说做就做，就有了这个个人博客系统。起初做的还算有激情，后来发现如果是一个人开发的话，那么自己不但需要做UI，做后台，还需要自己设计架构，设计数据库，需要学习的东西太多了，中间也想过放弃，但是还是坚持下来了。本来自己数学功底不好，导致算法功底也不好，一些难的功能自己也不能实现。只能做做简单的增删改查。希望这个博客项目能够锻炼自己的能力吧。
                    </p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        一句话勉励自己
                    </p>
                    <p>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        努力，奋斗！-----周星驰
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<%--顶部工具栏--%>
<script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="deleteChecked">删除</button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="removeChecked"><i class="layui-icon"></i>移除
        </button>
    </div>
</script>
<%--操作栏--%>
<div id="opbar">
    <a class="layui-btn layui-btn-xs" lay-event="update">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="remove">移除</a>
</div>
<%--修改图片弹出层--%>
<div id="form-update-container" class="layui-hide">
    <form class="layui-form" >
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 20px">ID</label>
            <div class="layui-input-block" style="margin-left: 55px;">
                <input type="text" name="id" required placeholder="id" autocomplete="off"
                       class="layui-input layui-disabled" disabled>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 20px">title</label>
            <div class="layui-input-block" style="margin-left: 55px;">
                <input type="text" name="title" required lay-verify="required" placeholder="请输入标题" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 20px;">src</label>
            <div class="layui-input-block" style="margin-left: 55px;">
                <input type="text" name="src" required lay-verify="required" placeholder="请输入src" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block" style="margin-left: 55px;">
                <button class="layui-btn" lay-submit lay-filter="btn-update">确认修改</button>
            </div>
        </div>
    </form>
</div>
<script src="${basePath}js/admin/img/datatable.min.js"></script>