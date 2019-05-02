<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${basePath}css/bloglist.min.css">
    <link rel="stylesheet" href="${basePath}css/articles.min.css">
    <link rel="stylesheet" href="${basePath}css/articles_side.min.css">
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="${basePath}js/common.min.js"></script>
    <script src="${basePath}js/articles.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<!--中间部分开始-->
<div class="content" id="content">
    <jsp:include page="articles_side.jsp"/>
    <div class="main fr">
        <div class="blog-index">
            <i class="fa fa-home home"></i>
            <a href="${basePath}articles?page=1&type=${type}">
                <c:if test="${type.equals('all')}">
                    技术文章
                </c:if>
                <c:if test="${!type.equals('all')}">
                    ${type}
                </c:if>
            </a>
        </div>
        <div class="blog-list">
            <c:if test="${pageInfo.total == 0}">
                <p style="text-align: center">没有数据</p>
            </c:if>
            <c:if test="${pageInfo.total > 0}">
                <c:forEach items="${pageInfo.list}" var="BKBlog">
                    <div class="blog-list-item">
                        <div class="item-title">
                            <span class="fl">${BKBlog.tag.title}</span>
                            <h2 class="fl" data-id="${BKBlog.id}">${BKBlog.title}</h2>
                        </div>
                        <div class="item-desc">
                            <div class="desc-img fl">
                                <img title="${BKBlog.title}" class="lazy" data-original="${BKBlog.coverImg.src}">
                            </div>
                            <div class="desc-text fr">
                                <p data-id="${BKBlog.id}">${BKBlog.summary}</p>
                            </div>
                        </div>
                        <div class="item-fork">
                    <span>
                        <i class="glyphicon glyphicon-eye-open"></i>
                        ${BKBlog.viewCount}&nbsp;浏览
                    </span>
                            <span>
                        <i class="glyphicon glyphicon-comment"></i>
                        ${BKBlog.commentCount}&nbsp;评论
                    </span>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <c:if test="${pageInfo.total > 0}">
            <div class="pages">
                <div class="pagination">
                    <ul>
                        <c:if test="${pageInfo.pageNum == 1}">
                            <li><a href="javascript:void(0)">上一页</a></li>
                        </c:if>
                        <c:if test="${pageInfo.pageNum != 1}">
                            <li><a href="${basePath}articles?page=${pageInfo.pageNum - 1}&type=${type}">上一页</a></li>
                        </c:if>
                        <c:forEach begin="1" end="${pageInfo.pages}" varStatus="status">
                            <c:if test="${pageInfo.pageNum == status.index}">
                                <li><a href="${basePath}articles?page=${status.index}&type=${type}"
                                       class="active">${status.index}</a></li>
                            </c:if>
                            <c:if test="${pageInfo.pageNum != status.index}">
                                <li><a href="${basePath}articles?page=${status.index}&type=${type}">${status.index}</a></li>
                            </c:if>
                        </c:forEach>
                        <c:if test="${pageInfo.pageNum == pageInfo.pages}">
                            <li><a href="javascript:void(0)" class="disabled">下一页</a></li>
                        </c:if>
                        <c:if test="${pageInfo.pageNum != pageInfo.pages}">
                            <li><a href="${basePath}articles?page=${pageInfo.pageNum + 1}&type=${type}">下一页</a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </c:if>
    </div>
    <div class="clearfix"></div>
</div>
<!--中间部分结束-->
<jsp:include page="footer.jsp"/>
</body>
</html>
