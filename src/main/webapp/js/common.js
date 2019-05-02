// 站点名称
const WEBSIT_NAME = "myblog";
// fastdfs 文件系统的上传前缀
const FASTDFS_PREFIX = "https://www.ljtnono.cn";
// 默认的图片
const IMG_DEFAULT =  {
    id: "d39b45c076b14cf0a97cf1d9f6fa6363",
    title: "img_default",
    src: FASTDFS_PREFIX + "/group1/M00/00/00/rBKlY1x-YGWAH3TFAAAWufqxBS8650.jpg",
    updateDateTime : new Date(),
    createDateTime: new Date()
};
$(function () {
    //开启提示栏
    $("[data-toggle='tooltip']").tooltip();
    //点击回到顶部
    (function () {
        const $toTop = $("#toTop");
        $toTop.click(function () {
            //首先应该判断是否处于顶部
            if ($(window).scrollTop() !== 0) {
                //处于顶部的时候将按钮隐藏起来
                $('html,body').stop().animate({scrollTop:0},'linear',null,function () {
                    $toTop.stop().animate({opacity:'0'},'linear');
                });
            }
        });
        $(window).scroll(function () {
            if ($(window).scrollTop() !== 0) {
                $toTop.stop().animate({opacity: '1'},'linear');
            } else {
                $toTop.stop().animate({opacity: '0'},'linear');
            }
        })
    })();
    //修复火狐浏览器summary过长的问题
    (function () {
        //如果是火狐浏览器的话
        const browser = getBrowserInfo();//取到完整信息
        const b_name = (browser + "").replace(/[0-9./]/ig, "");//根据正则将所有数字、‘.’‘/’全部去掉，剩下浏览器名
        if (/firefox/.test(b_name.toLowerCase())) {
            const $blog_summary_list = $(".blog-summary-list");
            const $blog_summary_like = $(".blog-summary-like");
            $blog_summary_list.each(function () {
                const text = $(this).html();
                if (text.length >= 120) {
                    $(this).html(text.substring(0,120) + "...");
                }
            });
            $blog_summary_like.each(function () {
                const text = $(this).html();
                if (text.length >= 32) {
                    $(this).html(text.substring(0,32) + "...");
                }
            });
        }
    })();
    //浏览器类型及版本
    function getBrowserInfo() {
        var agent = navigator.userAgent.toLowerCase();
        var regStr_ie = /msie [\d.]+;/gi;
        var regStr_ff = /firefox\/[\d.]+/gi;
        var regStr_chrome = /chrome\/[\d.]+/gi;
        var regStr_saf = /safari\/[\d.]+/gi;
        var isIE = agent.indexOf("compatible") > -1 && agent.indexOf("msie" > -1); //判断是否IE<11浏览器
        var isEdge = agent.indexOf("edge") > -1 && !isIE; //判断是否IE的Edge浏览器
        var isIE11 = agent.indexOf('trident') > -1 && agent.indexOf("rv:11.0") > -1;
        if (isIE) {
            var reIE = new RegExp("msie (\\d+\\.\\d+);");
            reIE.test(agent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            if (fIEVersion === 7) {
                return "IE/7";
            } else if (fIEVersion === 8) {
                return "IE/8";
            } else if (fIEVersion === 9) {
                return "IE/9";
            } else if (fIEVersion === 10) {
                return "IE/10";
            }
        } //isIE end
        if (isIE11) {
            return "IE/11";
        }
        //firefox
        if (agent.indexOf("firefox") > 0) {
            return agent.match(regStr_ff);
        }
        //Safari
        if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
            return agent.match(regStr_saf);
        }
        //Chrome
        if (agent.indexOf("chrome") > 0) {
            return agent.match(regStr_chrome);
        }
    }
    $("img.lazy").lazyload({
        placeholder : IMG_DEFAULT.src, //用图片提前占位
        effect: "fadeIn",
        threshold: 100 // 提前开始加载
    });
});
//通用ajax请求代码
function ajaxNormal(ajaxJson) {
    var load;
    $.ajax({
        url : ajaxJson.url,
        method : "post",
        data : ajaxJson.data,
        dataType : "json",
        beforeSend: function () {
            layui.use("layer",function () {
               const layer = layui.layer;
               load = layer.load(3, {time: 10*1000});
            });
        },
        success : function (data) {
            ajaxJson.success(data);
        },
        complete: function () {
            layui.use("layer",function () {
                const layer = layui.layer;
                layer.close(load);
            });
        },
        error : function() {
            if (ajaxJson.error) {
                ajaxJson.error();
            } else {
                ajaxErrorNormal();
            }

        }
    })
}
//通用ajax error处理函数
function ajaxErrorNormal(message) {
    const m = message || "请求失败！";
    layui.use("layer",function () {
       const layer = layui.layer;
        layer.alert(m,{icon : 2},function (index) {
            layer.close(index);
        })
    });
}
//格式化日期函数  从EL表达式中获取的Date日期格式
function formatDate(date,format) {
    //这里date是一个字符串
    if (date !== null && date !== undefined) {
        const res = new Date(date);
        const year = res.getFullYear();
        const month = res.getMonth() + 1;
        const d = res.getDate();
        const hours = res.getHours();
        const minutes = res.getMinutes();
        const seconds = res.getSeconds();
        if (format === "yyyy-MM-dd") {
            return year + "-" + month + "-" + d;
        } else if (format === "yyyy-MM-dd hh:mm:ss") {
            return year + "-" + month + "-" + d + " " + hours + ":" + minutes + ":" + seconds;
        }
    }
}
// 日期转换显示
function dateToStr(date) {
    const time = new Date(date);
    return formatDate(time, "yyyy-MM-dd hh:mm:ss");
}



