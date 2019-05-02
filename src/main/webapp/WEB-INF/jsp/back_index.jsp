<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <title>JT Geek 后台管理系统</title>
    <link rel="stylesheet" href="${basePath}lib/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}lib/editor.md-master/css/editormd.css">
    <link rel="stylesheet" href="${basePath}lib/layui-v2.4.5/layui/css/layui.css">
    <link rel="stylesheet" href="${basePath}css/back_index.min.css">
    <link rel="shortcut icon" href="${basePath}images/logo.ico" />
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery_lazyload/1.9.7/jquery.lazyload.min.js"></script>
    <script src="${basePath}lib/layui-v2.4.5/layui/layui.js"></script>
    <script src="${basePath}lib/bootstrap/js/bootstrap.js"></script>
    <script src="${basePath}js/common.min.js"></script>
    <script src="${basePath}lib/editor.md-master/zepto.min.js"></script>
    <script src="${basePath}lib/editor.md-master/editormd.min.js"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <jsp:include page="back_header.jsp" flush="true"/>
    <jsp:include page="back_left.jsp" flush="true"/>
    <jsp:include page="${currentPageName}.jsp"/>
    <jsp:include page="back_footer.jsp" flush="true"/>
</div>
<script>
    layui.use('element', function () {
        var element = layui.element;
    });
    layui.use('layer', function(){
        var layer = layui.layer;
    });
</script>
</body>
</html>
