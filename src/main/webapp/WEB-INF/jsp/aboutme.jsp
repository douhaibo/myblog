<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
    <title>JT Geek</title>
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <link rel="shortcut icon" href="${basePath}images/logo.ico" />
    <link rel="stylesheet" href="${basePath}css/aboutme.min.css">
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="${basePath}js/aboutme.min.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="content layui-container" id="content">
    <%--header部分--%>
    <div class="header layui-row">
        <div class="layui-col-lg2 fl">
            <h1>关于自己</h1>
        </div>
        <div class="layui-col-lg10">
            <div class="fx fr">
                <button class="layui-btn layui-btn-radius fx-btn layui-main">
                    分享
                    <i class="layui-icon"></i>
                </button>
            </div>
        </div>
    </div>
    <%--个人信息部分--%>
    <div class="info">
        <div class="header layui-row">
            <p>个人简介</p>
        </div>
        <div class="info-detail">
            <img src="${basePath}images/album/me.jpg" title="me"
                 class="layui-circle info-avatarl layui-anim-scale layui-anim">
            <p>BruseLing</p>
            <p>爱生活、爱艺术、爱书画、爱音乐、爱设计、爱编程。</p>
            <p>人生百态，笑口常开，秉承自我，谨慎独行。静觅，静静寻觅生活的美好。</p>
        </div>
        <%--联系我--%>
        <div class="contactme layui-row">
            <a href="https://weibo.com/u/5464556037" target="_blank"><i class="fa fa-weibo weibo layui-circle"
                                                                        data-toggle="tooltip" title="新浪微博"
                                                                        data-placement="top"></i></a>
            <a href="javascript:void(0);"><i class="fa fa-wechat wechat layui-circle" data-toggle="popover"></i></a>
            <a href="javascript:void(0);"><i class="fa fa-qq qq layui-circle" data-toggle="tooltip" title="qq935188400"
                                             data-placement="top"></i></a>
            <a href="https://github.com/ljtnono" target="_blank"><i class="fa fa-github github layui-circle"
                                                                    data-toggle="tooltip"
                                                                    title="ljtnono" data-placement="top"></i></a>
            <a href="javascript:void(0);"><i class="fa fa-envelope-o envelope layui-circle" data-toggle="tooltip"
                                             title="email:935188400@qq.com" data-placement="top"></i></a>

        </div>
    </div>
    <%--技能树部分--%>
    <div class="skill-tree">
        <div class="header layui-row">
            <p>技能树</p>
        </div>
        <div class="skill-table"></div>
    </div>
    <%--时间轴部分--%>
    <div class="timeline">
        <div class="header layui-row">
            <p>时间轴</p>
        </div>
        <div class="timeline-line">
            <ul class="layui-timeline">

            </ul>
        </div>
    </div>
    <div class="book-table">
        <div class="header layui-row">
            <p>我的书单</p>
        </div>
        <div class="book-table-container">
            <table class="layui-table" lay-even="">
                <thead>
                <tr>
                    <th>书籍</th>
                    <th>出版时间</th>
                    <th>作者</th>
                    <th>简介</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="4">正在请求数据</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="pl">
        <div id="SOHUCS" sid="aboutme"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<!--畅言安装-->
<script charset="utf-8" type="text/javascript" src="https://changyan.sohu.com/upload/changyan.js"></script>
<script type="text/javascript">
    window.changyan.api.config({
        appid: 'cytUQAnDO',
        conf: 'prod_5fc10ddaf8f1357d705848d83bed078f'
    });
</script>
<script type="text/javascript" src="http://assets.changyan.sohu.com/upload/plugins/plugins.limit.js">
</script>
</body>
</html>
