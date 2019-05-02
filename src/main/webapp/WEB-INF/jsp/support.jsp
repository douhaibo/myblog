<%@ page contentType="text/html;charset=UTF-8" %>
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
    <link rel="stylesheet" href="${basePath}css/support.min.css">
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="${basePath}js/support.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="layui-container content" id="content">
    <%--header部分--%>
    <div class="header layui-row">
        <div class="layui-col-lg2 fl">
            <h1>打赏作者</h1>
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
    <%--支付方式1 支付宝--%>
    <div class="zfb">
        <div class="header layui-row">
            <h2>如果您喜欢我的文章，感觉我的文章对您有帮助，不妨动动您的金手指给予小额赞助，予人玫瑰，手有余香，不胜感激。</h2>
        </div>
        <div class="zfb-detail">
            <p>手机支付宝扫一扫</p>
            <img src="${basePath}images/ds.png" title="me"/>
        </div>
    </div>
    <%--支付方式2微信--%>
    <div class="wx">
        <div class="header layui-row">
            <h2>微信打赏</h2>
        </div>
        <div class="wx-detail">
            <p>微信扫一扫</p>
            <div class="pay">
                <ul class="pay-list">
                    <li>
                        <img src="${basePath}images/1.png" title="me"/>
                        <p class="pay-num ">￥1</p>
                    </li>
                    <li>
                        <img src="${basePath}images/5.png" title="me"/>
                        <p class="pay-num ">￥5</p>
                    </li>
                    <li>
                        <img src="${basePath}images/10.png" title="me"/>
                        <p class="pay-num ">￥10</p>
                    </li>
                    <li>
                        <img src="${basePath}images/20.png" title="me"/>
                        <p class="pay-num ">￥20</p>
                    </li>
                    <li>
                        <img src="${basePath}images/ANY.png" title="me"/>
                        <p class="pay-num ">￥ANY</p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <%--畅言--%>
    <div class="pl">
        <div id="SOHUCS" sid="support"></div>
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
