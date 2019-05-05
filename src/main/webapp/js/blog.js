$(function () {
    const $blogList = $(".blog-list");
    //点击recommendblog的title和summary部分进行跳转操作
    $blogList.on("click",".blog-list-item > .item-title > h2",function () {
        location.href = "/myblog/blog/article_detail?num=" + $(this).data("id");
    });
    $blogList.on("click",".blog-list-item > .item-desc > .desc-text > p ",function () {
       location.href = "/myblog/blog/article_detail?num=" + $(this).data("id");
    });
    // TODO 获取友情链接 hot5 标签云
    layui.use('flow', function() {
        // 列表懒加载
        var flow = layui.flow;
        flow.load({
            elem: '.blog-list', //流加载容器
            isAuto: false,
            isLazyimg:true,
            done: function (page, next) {
                var lis = [];
                $.ajax({
                    url : "/myblog/blog/getPageBlogList",
                    data : {
                        page : page,
                        limit : 12
                    },
                    method : "GET",
                    dataType : "json",
                    success : function (data) {
                        if (data.request==="success" && data.status === 200) {
                            //假设你的列表返回在data集合中
                            layui.each(data.data, function(index, item){
                                if (item.mode === 1) {
                                    var i = "<div class='blog-list-item'>" +
                                        "<div class='item-title'>" +
                                        "<span class='fl' data-id='"+item.tag.id+"'>" + item.tag.link.title + "</span>" +
                                        "<h2 class='fl' data-id='"+item.id+"'>" + item.title + "</h2>" +
                                        "</div>" +
                                        "<div class='item-desc'>" +
                                        "<div class='desc-img fl'>" +
                                        "<img title='" + item.title + "' lay-src='" + item.coverImg.src + "'>" +
                                        "</div>" +
                                        "<div class='desc-text fr'>" +
                                        "<p data-id='" + item.id + "' class='BKBlog-summary-list'>" + item.summary + "</p>" +
                                        "</div>" +
                                        "</div>" +
                                        "<div class='item-fork'>" +
                                        "<span>"+
                                        "<i class='glyphicon glyphicon-eye-open'></i>" +
                                        "" + item.viewCount + "&nbsp;浏览" +
                                        "</span>" +
                                        "<span>" +
                                        "<i class='glyphicon glyphicon-comment'></i>" +
                                        "" + item.commentCount + "&nbsp;评论" +
                                        "</span>" +
                                        "</div>" +
                                        "</div>";
                                    lis.push(i);
                                }
                            });
                            next(lis.join(''), page < data.totalCount / 10);
                        } else {
                            ajaxErrorNormal();
                        }
                    },
                    error : function() {
                        ajaxErrorNormal();
                    }
                });
            }
        });
    });
});