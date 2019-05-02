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
    <link rel="stylesheet" href="${basePath}css/base.min.css">
    <link rel="stylesheet" href="${basePath}css/articles_side.min.css">
    <link rel="stylesheet" href="${basePath}css/article_detail.min.css">
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <link rel="stylesheet" href="${basePath}lib/editor.md-master/css/editormd.css">
    <script src="${basePath}lib/editor.md-master/lib/marked.min.js"></script>
    <script src="${basePath}lib/editor.md-master/lib/prettify.min.js"></script>
    <script src="${basePath}lib/editor.md-master/lib/raphael.min.js"></script>
    <script src="${basePath}lib/editor.md-master/lib/underscore.min.js"></script>
    <script src="${basePath}lib/editor.md-master/lib/flowchart.min.js"></script>
    <script src="${basePath}lib/editor.md-master/lib/jquery.flowchart.min.js"></script>
    <script src="${basePath}lib/editor.md-master/lib/sequence-diagram.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<%--中间部分开始--%>
<div class="content" id="content">
    <jsp:include page="articles_side.jsp"/>
    <div class="main fr">
        <div class="main-header">
            <div class="main-header-nav">
                <i class="fa fa-home home"></i>
                <a href="${basePath}articles?page=1&type=${blog.tag.title}" class="article-tag">${blog.tag.title}</a>
                <p class="article-title clearfix">${blog.title}</p>
            </div>
            <div class="main-header-content">
                <h2 class="article-title">${blog.title}</h2>
                <div class="article-detail">
                    <a href="${basePath}articles?page=1&type=${blog.tag.title}" class="fa fa-list-alt alt">
                        ${blog.tag.title}
                    </a>
                    <span>
                        <i class="fa fa-clock-o date" id="BKBlog-updateDateTime"></i>
                        <script>
                            (function () {
                                var date = "${blog.updateDateTime.toLocaleString()}";
                                document.write(formatDate(date,"yyyy-MM-dd hh:mm:ss"));
                            })();
                        </script>
                    </span>
                    <span>
                        <i class="fa fa-comment-o comment"></i>
                        <a href="#SOHUCS" id="changyan_count_unit"></a>&nbsp评论
                    </span>
                </div>
            </div>
        </div>
        <div class="main-article" id="main-article">
            <textarea style="display:none;" id="article-show"></textarea>
            <textarea id="article-source" style="display:none;">${blog.contentMarkDown}</textarea>
        </div>
        <%--上一篇，下一篇信息--%>
        <div class="moreinfo">
            <div class="zz">
                <p>转载请注明：<a href="https://www.ljtnono.cn/myblog/">JTGeek</a><i class="fa fa-angle-double-right"></i><a
                        href="${basePath}/article_detail?num=${blog.id}">${blog.title}</a></p>
            </div>
            <div class="ds-fx">
                <div class="ds fl">
                    <span data-toggle="popover" id="ds">
                        <i class="fa fa-heart-o"></i>
                        打赏
                    </span>
                </div>
                <div class="fx fl">
                    <span>
                        <i class="fa fa-share-alt"></i>
                        分享
                        <div class="bshare-custom" style="position: absolute;top: 50%;margin-top: -8px;right: 0;">
                            <a title="分享" class="bshare-more bshare-more-icon more-style-addthis"></a>
                        </div>
                        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
                        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
                    </span>
                </div>
            </div>
<%--            <div class="next-prev">--%>
<%--                <a href="${basePath}article_detail?num=${prev.id}" class="BKBlog-prev fl">--%>
<%--                    <c:if test="${prev != null}">--%>
<%--                    <i class="fa fa-angle-double-left"></i>--%>
<%--                        &lt;%&ndash;${prev.title}&ndash;%&gt;--%>
<%--                        ${prev.title}--%>
<%--                    </c:if>--%>
<%--                </a>--%>
<%--                <a href="${basePath}article_detail?num=${next.id}" class="BKBlog-next fr">--%>
<%--                    <c:if test="${next != null}">--%>
<%--                        ${next.title}--%>
<%--                        <i class="fa fa-angle-double-right"></i>--%>
<%--                    </c:if>--%>
<%--                </a>--%>
<%--            </div>--%>
        </div>
        <div class="pl">
            <div id="SOHUCS" sid="${blog.id}"></div>
        </div>
    </div>
    <div class="clearfix"></div>
</div>
<%--中间部分结束--%>
<jsp:include page="footer.jsp"/>
<!--畅言安装-->
<script charset="utf-8" type="text/javascript" src="https://changyan.sohu.com/upload/changyan.js"></script>
<script type="text/javascript" src="http://assets.changyan.sohu.com/upload/plugins/plugins.limit.js"></script>
<script src="${basePath}lib/editor.md-master/editormd.js"></script>
<script type="text/javascript">
    window.changyan.api.config({
        appid: 'cytUQAnDO',
        conf: 'prod_5fc10ddaf8f1357d705848d83bed078f'
    });
    var testEditor;
    $("#article-show").val($("#article-source").val());
    $(function() {
        testEditor = editormd.markdownToHTML("main-article", {
            htmlDecode : "style,iframe,script",
            emoji : true,
            taskList : true,
            tex : false, // 默认不解析
            flowChart : false, // 默认不解析
            sequenceDiagram : false, // 默认不解析
            codeFold : false
        });
    });
</script>
</body>
</html>
