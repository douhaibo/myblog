$(function () {
   var wechat_qrcode = "<div class='wechat-popover'>" +
       "                <img src='images/qrcode.png' alt=''>" +
       "            </div>";
   $('i[data-toggle="popover"]').popover({
      animation: true,
      title: "关注我的微信",
      placement: "top",
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

   //将所有带有data-date属性的设置好时间的显示格式
   $("[data-date]").each(function (i,n) {
      var date = $(n).attr("data-date");
      var $dom = $(n);
      if ($dom.attr("data-date-place") === "html") {
         $dom.html(formatDate(date,"yyyy-MM-dd"))
      } else if ($dom.attr("data-date-place") === "value") {
         $dom.value(formatDate(date,"yyyy-MM-dd"))
      }
   });

   // 基于准备好的dom，初始化echarts实例
   var skilltree = echarts.init(document.getElementsByClassName("skill-table")[0]);
   var color = ["#49c085","#f2b63c","#f58a87","#6f92ff","#7782d1","#d56464"];
   var option = {
      title: {
         text: '个人技能'
      },
      tooltip: {},
      legend: {
         show: true
      },
      xAxis: {
         name : "技能",
         position: 'bottom',
         data: ["html/css", "js", "java", "linux", "ps", "andriod"]
      },
      yAxis: {
         name : "分数",
         min: 0,
         max: 100
      },
      series: [{
         name: '技能',
         type: 'bar',
         label : {
            show : true,
            position: "top"
         },
         data : [
             {name:"html/css",value:70,itemStyle: {color:color[0]}},
            {name:"js",value:62,itemStyle: {color:color[1]}},
            {name:"java",value:85,itemStyle: {color:color[2]}},
            {name:"linux",value:45,itemStyle: {color:color[3]}},
            {name:"ps",value:63,itemStyle: {color:color[4]}},
            {name:"android",value:56,itemStyle: {color:color[5]}}
            ]
      }]
   };
   $.ajax({
      url : "/myblog/getBKSkillAll",
      method : "GET",
      dataType: "json",
      success: function (data) {
         if (data.status === 200 && data.request === "success") {
            // 指定图表的配置项和数据
            var skill = [];
            var name = [];
            for (var i = 0; i < data.totalCount; i++) {
               skill.push({
                  name: data.data[i].name,
                  value: data.data[i].grade,
                  itemStyle: {
                     color: color[i % color.length]
                  }
               });
               name.push(data.data[i].name);
            }
            option.xAxis.data = name;
            option.series[0].data = skill;
            // 使用刚指定的配置项和数据显示图表。
            skilltree.setOption(option);
         }
      },
      error: function () {
         skilltree.setOption(option);
      }
   });


   $.ajax({
      url : "/myblog/getAboutMeBookList",
      method: "GET",
      dataType: "json",
      success: function (data) {
         if (data.request === "success" && data.totalCount > 0) {
            var s = "";
            data.data.forEach(function (item) {
               s += "<tr><td>"+item.name+"</td><td data-date='"+formatDate(item.publishDate,"yyyy-MM-dd")+"' data-date-place='html'>"+formatDate(item.publishDate,"yyyy-MM-dd")+"</td>" +
                   "<td>"+item.author+"</td>" + "<td>"+item.desc+"</td>" + "</tr>";
            });
            $(".book-table-container > table > tbody").html(s);
         }
      },
      error: function () {
         //生成一个按钮并且给按钮添加点击事件进行重新获取
         ajaxErrorNormal();
      }
   });

   $.ajax({
      url : "/myblog/getBKTimeLineAll",
      method: "GET",
      dataType: "json",
      success: function (data) {
         if (data.request === "success" && data.totalCount > 0) {
            data.data.forEach(function (item) {
               $(".timeline-line > .layui-timeline").append(
                   "<li class='layui-timeline-item layui-anim-up'>" +
                   "<i class='layui-icon layui-timeline-axis'></i>" +
                   "<div class='layui-timeline-content layui-text'>" +
                   "<div class='layui-timeline-title'>"+formatDate(item.date,"yyyy-MM-dd")+"--"+item.content+"</div> " +
                   "</div>" +
                   "</li>"
               );
            });
         }
      },
      error: function () {
         ajaxErrorNormal();
      }
   })
});