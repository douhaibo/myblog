<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<link rel="shortcut icon" href="${basePath}images/logo.ico" />
<link rel="stylesheet" href="${basePath}lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${basePath}lib/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet" href="${basePath}lib/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${basePath}css/base.min.css">
<link rel="stylesheet" href="${basePath}css/header.min.css">
<script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery_lazyload/1.9.7/jquery.lazyload.min.js"></script>
<script src="${basePath}lib/bootstrap/js/bootstrap.js"></script>
<script src="https://cdn.bootcss.com/echarts/4.2.0-rc.2/echarts.min.js"></script>
<script src="${basePath}lib/layui-v2.4.5/layui/layui.js"></script>
<script src="${basePath}js/common.min.js"></script>
<!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
<!--[if lt IE 9]>
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<!--头部开始-->
<div class="header" id="header">
    <div class="container">
        <div class="logo fl">
            <h1>
                <a href="/myblog">JT Geek</a>
            </h1>
        </div>
        <div class="nav fl">
            <ul>
                <c:forEach items="${applicationScope.category.items}" var="item">
                    <li>
                        <a href="${item.href}" class="${item.classNames} ">${item.title}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<!--头部结束-->
<script>
    //当前页面高亮
    (function () {
        const path = location.href;
        if (path.indexOf("/blog") !== -1) {
            $(".nav > ul > li:eq(0) > a").addClass("active");
        } else if (path.indexOf("/articles") !== -1) {
            $(".nav > ul > li:eq(1) > a").addClass("active");
        } else if (path.indexOf("/article_detail") !== -1) {
            $(".nav > ul > li:eq(1) > a").addClass("active");
        } else if (path.indexOf("/aboutme") !== -1) {
            $(".nav > ul > li:eq(3) > a").addClass("active");
        } else if (path.indexOf("/support") !== -1) {
            $(".nav > ul > li:eq(2) > a").addClass("active");
        }
    })();
    // 百度统计安装
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?14988abdb1364fbf989ccd8ee3a7fbf1";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>