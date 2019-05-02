<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<div class="layui-body">
    <div id="back-container">
        <div class="container-content-left">
            <div class="layui-card form-search-container">
                <form class="layui-form">
                    <div class="layui-form-item">
                        <label class="layui-form-label">查询链接</label>
                        <div class="layui-input-block form-search">
                            <input type="text" name="id" placeholder="请输入id"
                                   autocomplete="off" class="layui-input  fl">
                            <input type="text" name="title" placeholder="请输入标题"
                                   autocomplete="off" class="layui-input  fl">
                            <input type="text" name="href" placeholder="请输入地址"
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
                <table id="datatable-link" lay-filter="datatable-link"></table>
            </div>
        </div>
        <div class="container-content-right">
            <div class="weather">
                <!-- cloud -->
                <div id="weather-view-he"></div>
                <script>
                    WIDGET = {ID: 'stV1hFvYAg'};
                </script>
                <script type="text/javascript" src="https://apip.weatherdt.com/view/static/js/r.js?v=1111"></script>
            </div>
            <%--友情链接--%>
            <div class="link-friend-container">
                <div class="header">
                    <span>友情链接</span>
                </div>
                <div class="link-friend-content">
                    <%--这里可以加上默认的填充方式让页面变得好看些--%>
                </div>
            </div>
            <%--日历组件--%>
            <div class="calendar-container">
                <div class="header">
                    <span>日历</span>
                </div>
                <div class="calendar-content">
                    <div id="calendar"></div>
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
<%--修改链接弹出层--%>
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
            <label class="layui-form-label" style="width: 20px;">type</label>
            <div class="layui-input-block" style="margin-left: 55px;">
                <input type="text" name="type" required lay-verify="required" placeholder="请输入type" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 20px;position: relative;left: -40px;">classNames</label>
            <div class="layui-input-block" style="margin-left: 55px;">
                <input type="text" name="classNames" required lay-verify="required" placeholder="请输入classNames" autocomplete="off"
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
<script src="${basePath}js/back_link_datatable.min.js"></script>



