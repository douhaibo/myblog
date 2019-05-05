<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<div class="layui-body">
    <div id="back-container">
        <div class="container-content-left" style="background-color:#fff;">

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
            <!--模块未定-->
            <div class="x-container">

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
<script src="js/softinfo.min.js"></script>