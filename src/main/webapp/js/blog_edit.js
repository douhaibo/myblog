/**
 * 博客编辑器配置
 * @type {{emoji: boolean, saveHTMLToTextarea: boolean, imageFormats: string[], previewTheme: string, imageUploadURL: string, taskList: boolean, imageUpload: boolean, syncScrolling: string, editorTheme: string, onload: EDITORMD_CONFIG.onload, path: string, tocm: boolean, toolbarIcons: (function(): *), toolbarAutoFixed: boolean, width: string, flowChart: boolean, theme: string, placeholder: string, height: number}}
 */
const EDITORMD_CONFIG = {
    placeholder: '本编辑器支持Markdown编辑，左边编写，右边预览',
    height: 1000,
    width: "90%",
    syncScrolling: "single",
    path: "lib/editor.md-master/lib/",   //你的path路径（原资源文件中lib包在我们项目中所放的位置）
    theme: "default",//工具栏主题
    previewTheme: "default",//预览主题
    editorTheme: "neat",//编辑主题
    saveHTMLToTextarea: true,
    toolbarAutoFixed: true,
    emoji: false,
    taskList: true,
    tocm: true,
    flowChart: true,
    imageUpload: true,
    imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
    imageUploadURL: "/myblog/editormdUploadImg",
    onload: function (data) {

    },
    toolbarIcons: function () {
        // 自定义工具栏
        return ["bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|", "h1", "h2", "h3", "h4", "h5", "h6", "|", "list-ul", "list-ol", "hr", "|", "link", "reference-link", "image", "table", "datetime", "|", "watch", "preview", "clear", "search", "|", "help", "info"];
    }
};

/**
 * 从markdown内容中提取图片链接，只返回第一个作为图片的封面
 * @param markDown 要提取的markdown内容
 * @returns {string|*} 图片链接
 */
function getImgUrlFromMarkDown(markDown) {
    var reg = /!\[.*?\]\((.*?)\)/im;
    var matches = reg.exec(markDown);
    // 如果没有的话，那么返回默认图片src
    if (matches === null || matches === undefined || matches.length === 0) {
        return IMG_DEFAULT.src;
    } else {
        return matches[1];
    }
}

/**
 * 校验博客参数
 * @param blog 博客实体对象
 * @returns {boolean} 校验成功返回true 失败返回false
 */
function validate(blog) {
    const title = blog.title;
    const contentHtml = blog.contentHtml;
    const contentMarkDown = blog.contentMarkDown;
    const tagId = blog.tag.id;
    if (title === "" || title.length < 4 || title.length > 30) {
        layui.use("layer", function () {
            const layer = layui.layer;
            layer.alert("博客标题长度为4-30个字符！", {icon: 2}, function (index) {
                layer.close(index);
            })
        });
        return false;
    }
    if (contentHtml === "" || contentMarkDown === "") {
        layui.use("layer", function () {
            const layer = layui.layer;
            layer.alert("博客内容不能为空！", {icon: 2}, function (index) {
                layer.close(index);
            })
        });
        return false;
    }
    if (tagId === "") {
        layui.use("layer", function () {
            const layer = layui.layer;
            layer.alert("请选择一个博客的标签！", {icon: 2}, function (index) {
                layer.close(index);
            })
        });
        return false;
    }
    return true;
}

/**
 * 重置状态
 * @param editor 编辑器对象
 * @param title title输入框对象
 */
function clean(editor, title){
    editor.clean();
    title.val("");
}

/**
 * 发送添加博客的请求
 * @param blog 需要的请求
 * @param editor 编辑器
 * @param titleInput title编辑框对象
 */
function sendAddBlogRequest(blog, editor, titleInput) {
    var load;
    $.ajax({
        url: "/myblog/addBlog",
        data: JSON.stringify(blog),
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        beforeSend: function () {
            layui.use("layer",function () {
                const layer = layui.layer;
                load = layer.load(3, {time: 10*1000});
            });
        },
        success: function (data) {
            clean(editor, titleInput);
            layer.msg(data.message, function () {
                layer.close(pop);
            });
        },
        complete: function () {
            layui.use("layer",function () {
                const layer = layui.layer;
                layer.close(load);
            });
        },
        error: function () {
            ajaxErrorNormal();
            clean(editor, titleInput);
            layer.close(pop);
        }
    });
}


$(function () {
    const editor = editormd("blog-editormd", EDITORMD_CONFIG);
    var $release = $("#release");
    var $btnRelease = $("#btn-release");
    var pop;
    var titleInput = $("#title");
    //TODO 使用ajax渲染tag标签下拉列表
    $.ajax({
        url : "/myblog/getPageTagList",
        data : {
            pageParam : null
        },
        method : "GET",
        success : function (data) {
            if (data.request === "success" && data.status === 200) {

            } else {

            }
        },
        error : function () {
            // TODO 弹出提示框
        }
    });
    $btnRelease.click(function () {
        $release.removeClass("layui-hide");
        pop = layer.open({
            type: 1,
            title: "发表博客",
            closeBtn: 1,
            shadeClose: true,
            scrollbar: false,
            resize: false,
            content: $release,
            offset : "200px",
            area: ["500px", "363px"],
            end: function () {
                $release.addClass("layui-hide");
            }
        });
    });
    layui.use(['layer', 'form'], function () {
        var layer = layui.layer;
        var form = layui.form;
        form.on('submit(submitBlog)', function () {
            var title = $("#title").val().trim();
            var contentHtml = editor.getHTML().trim();
            var contentMarkDown = editor.getMarkdown().trim();
            var blog = {
                title: title,
                contentHtml: contentHtml,
                contentMarkDown: contentMarkDown,
                mode: 1,
                tag: {
                    id: $("#blogTag").val()
                },
                coverImg: {
                    src: getImgUrlFromMarkDown(contentMarkDown)
                }
            };
            if (validate(blog)) {
                sendAddBlogRequest(blog, editor, titleInput);
            }
            return false;
        });
    });
});
