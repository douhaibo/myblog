$(function () {

    //点击recommendblog的title和summary部分进行跳转操作
    $(".blog-list-item > .item-title > h2").click(function () {
        location.href = "/myblog/article_detail?num=" + $(this).data("id");
    });
    $(".blog-list-item > .item-desc > .desc-text > p").click(function () {
       location.href = "/myblog/article_detail?num=" + $(this).data("id");
    });



    /**
     * blog列表懒加载
     */
    layui.use('flow', function() {
        var flow = layui.flow;
        flow.load({
            elem: '.blog-list', //流加载容器
            isAuto: false,
            isLazyimg:true,
            done: function (page, next) { //加载下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/api/list?page='+page, function(res){
                    //假设你的列表返回在data集合中
                    layui.each(res.data, function(index, item){
                        lis.push('<li>'+ item.title +'</li>');
                    });
                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < res.pages);
                });
            }
        });
    });


});