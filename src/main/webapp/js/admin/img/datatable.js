/**
 * img数据表格的配置
 * @type {{elem: string, toolbar: string, response: {countName: string, statusName: string, msgName: string, statusCode: number}, page: boolean, cols: *[][], url: string, height: number}}
 */
const TABLE_IMG_CONFIG = {
    elem: '#datatable-img',
    height: 470,
    url: '/myblog/img/getPageImgList',
    page: true,
    toolbar:"#toolbar",
    cols: [[
        {type: 'checkbox', fixed: 'left'},
        {field: 'id', title: 'ID', fixed: 'left'},
        {field: 'title', title: '标题'},
        {field: 'src', title: 'src'},
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
        {field: 'mode', title: '模式', width: 80, sort: true,templet:function (d) {
                if (d.mode === 0) {
                    return "已删除";
                } else {
                    return "正常";
                }
            }},
        {fixed: 'right', title: '操作', templet: "#opbar",width:180}
    ]],
    response: {
        statusName: "status",
        statusCode: 200,
        countName: "totalCount",
        msgName: "message"
    }
};
/**
 * 修改表单container
 * @type {jQuery.fn.init|jQuery|HTMLElement}
 */
const $FORM_UPDATE_CONTAINER = $("#form-update-container");
/**
 * 开启作者寄语的提示功能
 */
$(".exclamation").mouseover(function () {
    layer.tips('学习使我快乐！', '.exclamation', {
        tips: 1,
        time:1000
    });
});

/**
 * 显示弹出层
 * 把对应的值填充到表单中去
 * @param data 表单数据对象
 */
function showLayer(data) {
    $FORM_UPDATE_CONTAINER.removeClass("layui-hide");
    layer.open({
        type: 1,
        title: "修改图片信息",
        closeBtn: 1,
        shadeClose: true,
        content: $FORM_UPDATE_CONTAINER,
        success: function () {
            $("#form-update-container  input[name='id']").val(data.id);
            $("#form-update-container  input[name='title']").val(data.title);
            $("#form-update-container  input[name='src']").val(data.src);
        },
        end: function () {
            $FORM_UPDATE_CONTAINER.addClass("layui-hide");
        }
    });
}
/**
 *
 * @param id 要删除的img的id
 * @param remove 是否从数据库中删除
 */
function deleteImgById(id,remove) {
    const message = remove ? "真的移除么？会在文件系统中删除" : "真的删除么？";
    layer.confirm(message, function (index) {
        layer.close(index);
        $.ajax({
            url: "/myblog/img/deleteImgById",
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
 * 删除选中的元素
 * @param params  选中行的id 数组
 * @param remove 是否移除
 */
function deleteChecked(params,remove) {
    const message = remove ? "确认移除么？" : "确认删除么？";
    layer.confirm(message, function (index) {
        layer.close(index);
        $.ajax({
            url: "/myblog/img/deleteImgsByIdArray",
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
 * img 数据表格
 */
layui.use('table', function(){
    const table = layui.table;
    table.render(TABLE_IMG_CONFIG);
    table.on('tool(datatable-img)',function (obj) {
        const data = obj.data;
        const layEvent = obj.event;
        if (layEvent === 'update') {
            showLayer(data);
        } else if (layEvent === 'delete') {
            if (data.mode === 0) {
                layer.msg("已经删除！");
                return;
            }
            deleteImgById(data.id, false);
        } else if (layEvent === "remove") {
            deleteImgById(data.id,true);
        }
    });

    //头工具栏事件
    table.on('toolbar(datatable-img)', function(obj){
        const event = obj.event;
        const checkStatus = table.checkStatus(obj.config.id);
        const checkedIdArray = [];
        const checkedArray = checkStatus.data;
        if (checkedArray.length === 0) {
            layer.msg("请选中元素后再进行操作");
            return;
        }
        for (let i = 0; checkedArray.length !== 0 && i < checkedArray.length; i++ ) {
            checkedIdArray.push(checkedArray[i].id);
        }
        if (event === "deleteChecked") {
            deleteChecked(checkedIdArray,false);
        } else if(event === "removeChecked"){
            deleteChecked(checkedIdArray,true);
        }
    });
});
/**
 * 更新img
 * @param img 要更新的img实体数据
 */
function updateImg(img) {
    let load;
    // TODO 验证
    $.ajax({
        url: "/myblog/img/updateImg",
        method: "POST",
        data: JSON.stringify(img),
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
 * 修改img的表单配置
 */
layui.use('form', function(){
    const form = layui.form;
    form.on('submit(btn-update)', function(data){
        const img = data.field;
        updateImg(img);
        return false;
    });
});
/**
 * 搜索img表单配置
 */
layui.use('form', function(){
    const form = layui.form;
    form.on('submit(btn-search)', function(data){
        const img = data.field;
        // TODO 检测参数合法性
        if (img.id === "" && img.title === "") {
            layui.use("table",function () {
                const table = layui.table;
                table.render(TABLE_IMG_CONFIG);
            });
        } else {
            layui.use("table",function () {
                const table = layui.table;
                table.reload('datatable-img',{
                    url : "/myblog/img/searchImg",
                    where : {
                        id: img.id,
                        title: img.title
                    },
                    page: {
                        curr: 1
                    }
                });
            });
        }
        return false;
    });
});







