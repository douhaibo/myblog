<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <ul class="layui-nav layui-nav-tree" lay-filter="test">
            <li class="layui-nav-item">
                <a class="" href="javascript:void(0);">博客管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${basePath}admin/blog/edit" target="_blank">编写博客</a></dd>
                    <dd><a href="${basePath}admin/blog/datatable">博客报表</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:void(0);">标签管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${basePath}admin/tag/datatable">标签报表</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:void(0);">用户管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${basePath}admin/user">用户报表</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:void(0);">图片管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${basePath}admin/img/datatable">图片报表</a></dd>
                    <dd><a href="${basePath}admin/img/upload">上传图片</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:void(0);">书籍管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${basePath}admin/book">书籍报表</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:void(0);">链接管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="${basePath}admin/link/datatable">链接报表</a></dd>
                </dl>
            </li>
        </ul>
    </div>
</div>
<script>
    //导航显示
    $(function () {
        $("li.layui-nav-item").removeClass("layui-nav-itemed");
        $("a[href='${basePath}admin/${module}/${currentPage}']").parents(".layui-nav-item").addClass("layui-nav-itemed");
    })
</script>