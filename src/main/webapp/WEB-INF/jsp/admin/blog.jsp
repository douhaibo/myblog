<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin=primary] {
        top: 4px;
        padding: 0;
    }
</style>
<div class="layui-body" style="margin: 48px 5% 0;">
    <table id="BKBlog-table" lay-filter="BKBlog-table"></table>
</div>
<%--操作栏--%>
<div id="opbar">
    <a class="layui-btn layui-btn-xs" lay-event="detail">详情</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</div>
<script>
    function dateToStr(date) {
        var time = new Date(date);
        return formatDate(time, "yyyy-MM-dd hh:mm:ss");
    }

    layui.use('element', function () {
        var element = layui.element;
    });
    layui.use('table', function () {
        var table = layui.table;
        //第一个实例
        table.render({
            elem: '#BKBlog-table',
            url: '/myblog/getBlogList', //数据接口
            response: {
                statusName: 'status',
                statusCode: 200,
                msgName: "message",
                countName: "totalCount"
            },
            page: true, //开启分页
            cols: [[ //表头
                {type: 'checkbox', fixed: 'left'},
                {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left'},
                 {field: 'title', title: '标题', width: 170},
                 {field: 'tag', title: '标签', width: 100, sort: true, templet: '<div>{{d.tag.title}}</div>'},
                 {field: 'summary', title: '简介', width: 180},
                 {
                    field: 'createDateTime',
                    title: '创建时间',
                    width: 170,
                    templet: '<div>{{dateToStr(d.createDateTime)}}</div>'
                },
                 {
                    field: 'updateDateTime',
                    title: '最后修改时间',
                    width: 170,
                    sort: true,
                    templet: '<div>{{dateToStr(d.updateDateTime)}}</div>'
                },
                {field: 'viewCount', title: '浏览量', width: 80, sort: true},
                {field: 'commentCount', title: '评论数', width: 80},
                {field: 'imageId', title: '图片id', width: 80},
                {fixed: 'right', title: '操作', templet: "#opbar"}
            ]]
        });

        //监听操作栏的按钮点击事件
        table.on("tool(BKBlog-table)", function (obj) {
            //获取当前行的数据
            var data = obj.data;
            //如果是查看详情的话，那么直接跳转到相应的博客显示页面
            if (obj.event === "detail") {
                window.open("${basePath}article_detail?num=" + data.id);
            } else if (obj.event === "del") {
                //删除业务
            }
            //console.log(obj);
        })

    });
</script>



