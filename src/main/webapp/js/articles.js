$(function () {

    //猜你喜欢的时间格式转换
    (function () {
        var $orig = $(".date");
        var time = new Date($orig.html());
        var year = time.getFullYear();
        var month = time.getMonth() + 1;
        var date = time.getDate();
        var d = year + "-" + month + "-" + date;
        $orig.html(d);
    })();

    //点击recommendblog的title和summary部分进行跳转操作
    $(".blog-list-item > .item-title > h2").click(function () {
        location.href = "/myblog/blog/article_detail?num=" + $(this).data("id");
    });

    $(".blog-list-item > .item-desc > .desc-text > p").click(function () {
       location.href = "/myblog/blog/article_detail?num=" + $(this).data("id");
    });

});


