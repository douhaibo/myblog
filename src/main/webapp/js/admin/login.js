$(function () {
    //输入过滤
    $("#loginform").submit(function () {
        const $user = $(".user");
        const $psw = $(".psw");
        if (testUser($user)) {
            //发送请求
            if (testPsw($psw)) {
                return true;
            } else {
                layui.use("layer", function () {
                    layer.msg("密码不能为空！");
                });
                return false;
            }
        } else {
            layui.use("layer", function () {
                layer.msg("请检查用户名的正确性！用户名不能包含中文，是数字和字母的6-10位组合");
            });
            return false;
        }
    });

    //验证user
    function testUser($user) {
        //验证是否有中文
        const testText = $user.val().trim();
        if (/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(testText)) {
            return false;
        }
        //过滤特殊字符
        if (/[\W]/.test(testText)) {
            return false;
        }
        //检测位数
        return !(testText.length < 6 || testText.length > 10);
    }
    //检测密码是否为空
    function testPsw($psw) {
        const testText = $psw.val().trim();
        return testText.length !== 0;
    }
});