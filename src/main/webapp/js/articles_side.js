$(function () {
    /*开启tooltip插件*/
    $("[data-toggle='tooltip']").tooltip();
    /*开启弹出框插件*/
    var wechat_qrcode = "<div class='wechat-popover'>" +
        "                <img src='images/qrcode.png' alt=''>" +
        "            </div>";
    var ds = "<div class='wechat-popover'>" +
        "                <img src='images/ds.png' alt=''>" +
        "            </div>";
    $('i[data-toggle="popover"]').popover({
        animation: true,
        title: "关注我的微信",
        placement: "bottom",
        trigger: "manual",
        html: true,
        content: wechat_qrcode
    }).on("mouseenter", function () {
        var that = this;
        $(this).popover("show");
        $(this).siblings(".popover").on("mouseleave", function () {
            $(that).popover('hide');
        });
    }).on("mouseleave", function () {
        var that = this;
        setTimeout(function () {
            if (!$(".popover:hover").length) {
                $(that).popover("hide")
            }
        }, 100);
    });
    $('span#ds[data-toggle="popover"]').popover({
        animation: true,
        title: "打赏博主",
        placement: "top",
        trigger: "manual",
        html: true,
        content: ds
    }).on("mouseenter", function () {
        var that = this;
        $(this).popover("show");
        $(this).siblings(".popover").on("mouseleave", function () {
            $(that).popover('hide');
        });
    }).on("mouseleave", function () {
        var that = this;
        setTimeout(function () {
            if (!$(".popover:hover").length) {
                $(that).popover("hide")
            }
        }, 100);
    });

    //书单鼠标移入移出效果
    $(".book-item").on("mouseenter", function () {
        //获取当前那个隐藏hidden的那个
        var $titlehidden = $(".section-books-body").find("a.hidden");
        $titlehidden.removeClass("hidden");
        //将那个item的高设置为30px
        $titlehidden.parent().css("height", "30px");
        $titlehidden.siblings(".book-desc").addClass("hidden");
        //1.选择当前的book-title 移除hidden属性
        $(this).css("height", "135px");
        var $desc = $(this).children(".book-desc").removeClass("hidden");
        $(this).children(".book-title").addClass("hidden");
    });
});