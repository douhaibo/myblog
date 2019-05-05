/**
 *  link 数据表格配置
 * @type {{elem: string, toolbar: string, response: {countName: string, statusName: string, msgName: string, statusCode: number}, page: boolean, cols: *[][], url: string, height: number}}
 */
const TABLE_LINK_CONFIG = {
    elem: '#datatable-link',
    height: 470,
    url: '/myblog/link/getPageLinkList',
    page: true,
    toolbar: "#toolbar",
    cols: [[
        {type: 'checkbox', fixed: 'left'},
        {field: 'id', title: 'ID', fixed: 'left'},
        {field: 'title', title: '标题'},
        {field: 'href', title: '地址'},
        {
            field: 'type', title: '类型',
            templet: function (d) {
                if (d.type === 1) {
                    return "本地链接";
                } else if (d.type === 2) {
                    return "外链";
                }
            }
        },
        {
            field: 'createDateTime',
            title: '创建时间',
            sort: true,
            templet: function (d) {
                return dateToStr(d.createDateTime);
            }
        },
        {
            field: 'updateDateTime',
            title: '最后修改时间',
            width: 170,
            sort: true,
            templet: function (d) {
                return dateToStr(d.updateDateTime);
            }
        },
        {
            field: 'mode', title: '模式', width: 80, sort: true, templet: function (d) {
                if (d.mode === 0) {
                    return "已删除";
                } else {
                    return "正常";
                }
            }
        },
        {fixed: 'right', title: '操作', templet: "#opbar", width: 180}
    ]],
    response: {
        statusName: "status",
        statusCode: 200,
        countName: "totalCount",
        msgName: "message"
    }
};
/**
 * 更新 表单容器
 * @type {jQuery.fn.init|jQuery|HTMLElement}
 */
const $FORM_UPDATE_CONTAINER = $("#form-update-container");

/**
 * 删除选中的元素
 * @param params  选中行的id 数组
 * @param remove 是否移除
 */
function deleteChecked(params,remove) {
    const message = remove ? "确认移除么？" : "确认删除么？";
    layer.confirm(message, function (index) {
        layer.close(index);
        $.ajax({
            url: "/myblog/link/deleteLinksByIdArray",
            data: {
                params: params,
                remove: remove
            },
            dataType: "json",
            method: "GET",
            success: function (data) {
                if (data.status === 200 && data.request === "success") {
                    layer.msg(data.message);
                } else {
                    layer.msg(data.message, function () {

                    });
                }
            },
            error: function () {
                ajaxErrorNormal();
            }
        })
    });
}

/**
 * 显示弹出层
 * 把对应的值填充到表单中去
 * @param data 表单数据对象
 */
function showLayer(data) {
    $FORM_UPDATE_CONTAINER.removeClass("layui-hide");
    layer.open({
        type: 1,
        title: "修改链接信息",
        closeBtn: 1,
        shadeClose: true,
        content: $FORM_UPDATE_CONTAINER,
        success: function () {
            $("#form-update-container  input[name='id']").val(data.id);
            $("#form-update-container  input[name='title']").val(data.title);
            $("#form-update-container  input[name='type']").val(data.type);
            $("#form-update-container  input[name='classNames']").val(data.classNames);
        },
        end: function () {
            $FORM_UPDATE_CONTAINER.addClass("layui-hide");
        }
    });
}

/**
 * 根据id删除一个链接  只是set mode = 0
 * @param id 要删除的链接的id
 * @param remove 是否移除
 */
function deleteLinkById(id,remove) {
    const message = remove ? "真的移除么" : "真的删除么";
    layer.confirm(message, function (index) {
        layer.close(index);
        $.ajax({
            url: "/myblog/link/deleteLinkById",
            data: {
                id: id,
                remove: remove
            },
            dataType: "json",
            method: "GET",
            success: function (data) {
                if (data.status === 200 && data.request === "success") {
                    layer.msg(data.message);
                } else {
                    layer.msg(data.message, function () {
                    });
                }
            },
            error: function () {
                ajaxErrorNormal();
            }
        })
    });
}

/**
 * 修改链接信息
 * @param link 提交的link表单数据
 */
function updateLink(link) {
    // TODO 数据校验  这里由于标签有点不一样，所以更新标签的功能等到blog部分写完再慢慢写
    let load;
    $.ajax({
        url: "/myblog/link/updateLink",
        method: "POST",
        data: JSON.stringify(link),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        beforeSend: function () {
            layui.use("layer", function () {
                const layer = layui.layer;
                load = layer.load(3, {time: 10 * 1000});
            });
        },
        success: function (data) {
            if (data.status === 200 && data.request === "success") {
                layer.msg(data.message);
            } else {
                layer.msg(data.message, function () {
                });
            }
        },
        complete: function () {
            layui.use("layer", function () {
                const layer = layui.layer;
                layer.close(load);
            });
        },
        error: function () {
            ajaxErrorNormal();
        }
    });
}
/**
 * 数据表格
 */
layui.use('table', function () {
    const table = layui.table;
    table.render(TABLE_LINK_CONFIG);
    table.on('tool(datatable-link)',function (obj) {
        const data = obj.data;
        const layEvent = obj.event;
        if (layEvent === 'update') {
            showLayer(data);
        } else if (layEvent === 'delete') {
            if (data.mode === 0) {
                layer.msg("已经删除！");
                return;
            }
            deleteLinkById(data.id,false);
        } else if (layEvent === "remove") {
            deleteLinkById(data.id,true);
        }
    });
    table.on('toolbar(datatable-link)', function(obj){
        const event = obj.event;
        const checkStatus = table.checkStatus(obj.config.id);
        const params = [];
        const checkedArray = checkStatus.data;
        for (let i = 0; checkedArray.length !== 0 && i < checkedArray.length; i++ ) {
            params.push(checkedArray[i].id);
        }
        if (event === "deleteChecked") {
            if (checkedArray.length === 0) {
                layer.msg("请选中元素后再进行操作");
                return;
            }
            deleteChecked(params,false);
        } else if(event === "removeChecked"){
            if (checkedArray.length === 0) {
                layer.msg("请选中元素后再进行操作");
                return;
            }
            deleteChecked(params,true);
        }
    });
});
/**
 * 修改link的表单设置
 */
layui.use('form', function(){
    const form = layui.form;
    form.on('submit(btn-update)', function(data){
        const link = data.field;
        updateLink(link);
        return false;
    });
});
/**
 * 查询link的表单设置
 */
layui.use('form', function(){
    const form = layui.form;
    form.on('submit(btn-search)', function(data){
        const link = data.field;
        // TODO 验证参数正确性
        if (link.title === "" && link.id === "" && link.href === "") {
            //没有写查询条件，那就查询所有
            layui.use("table",function () {
                const table = layui.table;
                table.render(TABLE_LINK_CONFIG);
            });
        } else {
            // 将数据更新到表格当中去
            layui.use("table",function () {
                const table = layui.table;
                table.reload('datatable-link',{
                    url : "/myblog/link/searchLink",
                    where : data.field,
                    page: {
                        curr: 1
                    }
                });
            });
        }
        return false;
    });
});
/**
 * 使用日历组件
 */
layui.use('laydate', function () {
    const laydate = layui.laydate;
    laydate.render({
        elem: '#calendar',
        position: 'static',
        mark: {
            '0-03-30': '生日',
            '0-12-31': '跨年'
        }
    });
});

$(function () {
    (function () {
        const $content = $(".link-friend-content");
        $.ajax({
            url : "/myblog/link/getFriendLinkList",
            method: "GET",
            dataType: "json",
            success: function (data) {
                if (data.request === "success" && data.status === 200) {
                    const links = data.data;
                    // 拼接字符串
                    let result = "";
                    for (let i = 0;i < links.length; i++) {
                        result += "<div class='link'>" +
                            "<a href='"+links[i].href+"' target='_blank'>" +
                            "<i class='fa fa-link'>" +
                            "</i>" +
                            links[i].title +
                            "</a>" +
                            "</div>";
                    }
                    $content.html(result);
                } else {
                    ajaxErrorNormal(data.message);
                }
            },
            error:function () {
                ajaxErrorNormal();
            }
        })
    })();
});

