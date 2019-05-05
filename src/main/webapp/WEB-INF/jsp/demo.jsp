<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
    <title>WebSocket</title>
    <style>
        .show {
            width: 600px;
            height: 600px;
            background-color: #CCCCCC;
            margin: 0 auto;
            line-height: 200px;
        }
    </style>
</head>
<body>
    <div class="show"></div>
</body>

<script type="text/javascript">
    var url = 'ws://localhost:8080/myblog/websocket/systemInfo';
    var sock = new WebSocket(url);
    sock.onopen = function() {
        console.log('开启WebSocket连接！');
        sayHello();
    };
    sock.onmessage = function(e) {
        console.log('接受消息： ', e.data);
        setTimeout(function(){sayHello()}, 2000);
    };
    sock.onclose = function() {
        console.log('关闭WebSocket连接！');
    };
    function sayHello() {
        console.log('发送消息： hello world！')
        sock.send('hello world....');
    }
</script>
</html>
