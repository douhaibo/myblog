<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<link rel="stylesheet" href="${basePath}css/footer.min.css">
    <!--底部开始-->
    <div class="footer" id="footer">
        <div class="footer-inner">
            <div class="copyright">
                <a href="${basePath}/myblog/">${applicationScope.webConfig.blogAuthor}</a>
                版权所有 | 改自
                <a href="https://yusi123.com/" target="_blank">${applicationScope.webConfig.from}</a>
                主题 | 基于 SSM 构建 © 2018.10.29
                -
                <script>
                    var date = new Date();  //创建对象
                    var y = date.getFullYear();     //获取年份
                    document.write(y + "." + (date.getMonth() + 1) + "." + date.getDate());
                </script>
                <a href="${basePath}/myblog/">${applicationScope.webConfig.record}</a>
            </div>
        </div>
    </div>
    <%--点击回到顶部--%>
    <a href="javascript:void(0);" id="toTop" ></a>
    <!--底部结束-->



