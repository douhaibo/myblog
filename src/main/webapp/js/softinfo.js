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