<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<script src="${basePath}js/articles_side.min.js"></script>
<div class="side fl">
    <div class="section-share">
        <a href="https://weibo.com/u/5464556037" target="_blank">
            <i class="fa fa-weibo weibo" data-toggle="tooltip" title="新浪微博" data-placement="top"></i>
        </a>
        <a href="javascript:void(0);">
            <i class="fa fa-wechat wechat" data-toggle="popover"></i>
        </a>
        <a href="javascript:void(0);">
            <i class="fa fa-qq qq" data-toggle="tooltip" title="qq935188400" data-placement="top"></i>
        </a>
        <a href="https://github.com/ljtnono" target="_blank">
            <i class="fa fa-github github" data-toggle="tooltip" title="ljtnono" data-placement="top"></i>
        </a>
        <a href="javascript:void(0);">
            <i class="fa fa-envelope-o envelope" data-toggle="tooltip" title="email:935188400@qq.com"
               data-placement="top"></i>
        </a>
    </div>
    <div class="section-tags">
        <div class="section-tags-head">
            <p>分类标签</p>
        </div>
        <div class="section-tags-body">
            <c:forEach items="${articleTags}" var="tag">
                <div class="tag">
                    <a href="${tag.link.href}">
                        <i class="fa fa-tag"></i>
                            ${tag.title}
                    </a>
                </div>
            </c:forEach>
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
    <div class="section-guess">
        <div class="section-guess-head">
            <p>猜你喜欢</p>
        </div>
        <div class="section-guess-body">
            <ul>
                <c:forEach items="${articleLikeBlogs}" var="BKBlog">
                    <li>
                        <div class="fl">
                            <a href="javascript:void(0)">
                                <img title="${BKBlog.title}" class="lazy" data-original="${BKBlog.coverImg.src}">
                            </a>
                        </div>
                        <div class="fr">
                            <a href="${basePath}article_detail?num=${BKBlog.id}"
                               class="BKBlog-summary-like">${BKBlog.summary}</a>
                            <span class="date fa fa-clock-o">
                                <script>
                                    (function () {
                                        var date = "${BKBlog.updateDateTime.toLocaleString()}";
                                        document.write(formatDate(date, "yyyy-MM-dd"));
                                    })();
                                </script>
                            </span>
                            <a class="pl" href="javascript:void(0)">${BKBlog.commentCount}评论</a>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="section-books">
        <div class="section-books-head">
            <p>我的书单</p>
        </div>
        <div class="section-books-body">
            <c:forEach items="${myBookList}" var="book" varStatus="status">
                <c:if test="${status.index == 0}">
                    <div class="book-item">
                        <span class="book-index">${status.index + 1}</span>
                        <a class="book-title hidden" href="javascript:;">
                                ${book.name}
                        </a>
                        <div class="book-desc">
                            <div class="fl">
                                <img src="${defaultImg.src}" class="lazy" data-original="${book.img.src}">
                            </div>
                            <div class="fr">
                                <a class="book-detail" href="javascript:;">
                                        ${book.desc}
                                </a>
                                <span class="fa fa-rmb rmb">${book.price}</span>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${status.index != 0}">
                    <div class="book-item" style="height: 30px;">
                        <span class="book-index">${status.index + 1}</span>
                        <a class="book-title" href="javascript:;">
                                ${book.name}
                        </a>
                        <div class="book-desc hidden">
                            <div class="fl">
                                <img src="${book.img.src}" alt="">
                            </div>
                            <div class="fr">
                                <a class="book-detail" href="javascript:;">
                                        ${book.desc}
                                </a>
                                <span class="fa fa-rmb rmb">${book.price}</span>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div class="section-links">
        <div class="section-links-head">
            <p>友情链接</p>
        </div>
        <div class="section-links-body">
            <ul>
                <c:forEach items="${articleFriendLinks}" var="link">
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
</div>
