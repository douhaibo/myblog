<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JT Geek</title>
    <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
    <link rel="shortcut icon" href="${basePath}images/logo.ico" />
    <link rel="stylesheet" type="text/css" href="${basePath}engine1/style.css"/>
    <link rel="stylesheet" href="${basePath}css/bloglist.min.css">
    <link rel="stylesheet" href="${basePath}css/blog.min.css">
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="${basePath}js/tags.min.js"></script>
    <script src="${basePath}js/blog.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<!--中间部分开始-->
<div class="content" id="content">
    <div class="side fl">
        <div class="section-me">
            <div class="section-me-head">
                <p>站长简介</p>
            </div>
            <div class="section-me-body">
                <div class="section-me-body-img fl">
                    <img data-original="${basePath}images/album/me.jpg"  title="@www.ljtnono.cn/myblog" class="lazy">
                </div>
                <div class="section-me-body-text">
                    <p>
                        站长：BruseLing<br>
                        个人简介：一个内向但是开朗乐观的大学生，爱生活，爱java，希望能够成为一名
                        技术大牛，最想去的公司是华为，最崇拜的人詹姆斯高斯林，因为他竟然发明了java这种
                        牛逼的语言。<br>
                        文章：java，spring，mybatis,html，css，js，jquery
                        seo优化，建站心得，mysql，bootstrap,技术交流<br>
                        兴趣：写代码，打羽毛球 <br>
                    </p>
                </div>
            </div>
        </div>
        <div class="section-album">
            <div class="section-album-head">
                <p>我的相册</p>
            </div>
            <div class="section-album-body">
                <c:forEach items="${album}" var="img">
                    <div class="section-album-body-img">
                        <a href="javascript:void(0)">
                            <img title="${img.title}" data-original="${img.src}" class="lazy"  >
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="section-search">
            <input type="text" class="search" placeholder="请输入搜索内容"/>
            <input type="button" class="btn btn-sm" value="搜索"/>
        </div>
        <div class="section-tags">
            <div class="section-tags-head">
                <p>标签云</p>
            </div>
            <div class="section-tags-body">
                <div id="tagbox">
                    <c:forEach items="${cloudTags}" var="tag">
                        <a href="${tag.link.href}" class="${tag.link.classNames} ">${tag.link.title}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="section-hot">
            <div class="section-hot-head">
                <p>hot5</p>
            </div>
            <div class="section-hot-body">
                <div class="section-hot-body-list">
                    <ul>
                        <c:forEach items="${hotBKBlogList}" var="BKBlog">
                            <li>
                                <a href="${basePath}article_detail?num=${BKBlog.id}" data-url="${BKBlog.id}">
                                        ${BKBlog.title}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="section-links">
            <div class="section-links-head">
                <p>友情链接</p>
            </div>
            <div class="section-links-body">
                <ul>
                    <c:forEach items="${friendLinks}" var="link">
                        <li>
                            <a href="${link.href}" target="_blank" class="${link.classNames} ">
                                <i class="fa fa-link"></i>
                                    ${link.title}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="section-qrcode">
            <div class="section-qrcode-head">
                <p>关注我的微信</p>
            </div>
            <div class="section-qrcode-body">
                <img data-original="${basePath}images/qrcode.png"  title="加我微信" class="lazy" width="250" height="250">
            </div>
        </div>
    </div>
    <div class="main fr">
        <div class="slider">
            <div id="wowslider-container1">
                <div class="ws_images">
                    <ul>
                        <li><img src="${basePath}data1/images/sunset.jpg" alt="sunset" title="sunset" id="wows1_0"/>
                        </li>
                        <li><img src="${basePath}data1/images/leaves.jpg" alt="leaves" title="leaves" id="wows1_1"/>
                        </li>
                        <li><a href="http://wowslider.net"><img src="${basePath}data1/images/coffee.jpg"
                                                                alt="image slider" title="coffee" id="wows1_2"/></a>
                        </li>
                        <li><img src="${basePath}data1/images/bird.jpg" alt="bird" title="bird" id="wows1_3"/></li>
                    </ul>
                </div>
                <div class="ws_bullets">
                    <div>
                        <a href="#" title="sunset"><span><img src="${basePath}data1/tooltips/sunset.jpg" alt="sunset"/>1</span></a>
                        <a href="#" title="leaves"><span><img src="${basePath}data1/tooltips/leaves.jpg" alt="leaves"/>2</span></a>
                        <a href="#" title="coffee"><span><img src="${basePath}data1/tooltips/coffee.jpg" alt="coffee"/>3</span></a>
                        <a href="#" title="bird"><span><img src="${basePath}data1/tooltips/bird.jpg"
                                                            alt="bird"/>4</span></a>
                    </div>
                </div>
                <div class="ws_script" style="position:absolute;left:-99%"><a href="http://wowslider.net">bootstrap
                    carousel example</a> by WOWSlider.com v8.8
                </div>
                <div class="ws_shadow"></div>
            </div>
            <script type="text/javascript" src="${basePath}engine1/wowslider.js"></script>
            <script type="text/javascript" src="${basePath}engine1/script.js"></script>
        </div>
        <div class="blog-list">
            <c:if test="${recommendBKBlogList.size() == 0}">
                <div style="width: 100%;height: 1960px;text-align: center;">
                    <span>没有数据</span>
                </div>
            </c:if>
            <c:if test="${recommendBKBlogList.size() != 0}">
                <c:forEach items="${recommendBKBlogList}" var="BKBlog">
                    <div class="blog-list-item">
                        <div class="item-title">
                            <span class="fl" data-id="${BKBlog.tag.id}">${BKBlog.tag.link.title}</span>
                            <h2 class="fl" data-id="${BKBlog.id}">${BKBlog.title}</h2>
                        </div>
                        <div class="item-desc">
                            <div class="desc-img fl">
                                <img title="${BKBlog.title}" class="lazy" data-original="${BKBlog.coverImg.src}">
                            </div>
                            <div class="desc-text fr">
                                <p data-id="${BKBlog.id}" class="BKBlog-summary-list">${BKBlog.summary}</p>
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
    </div>
    <div class="clearfix"></div>
</div>
<!--中间部分结束-->
<jsp:include page="footer.jsp"/>
</body>
</html>
