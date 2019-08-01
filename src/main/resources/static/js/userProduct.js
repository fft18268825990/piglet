var table;
var layer;
var laydate;
layui.use(['util','table','layer','laydate'], function() {
    table = layui.table;
    util = layui.util;
    layer = layui.layer;
    laydate = layui.laydate;
    laydate.render({
        elem: '#createTimeRange'
        ,type: 'datetime'
        ,range: true
        ,done : function(value, date) {
            $('#startTime').val(value.substring(0,19));
            $('#endTime').val(value.substring(value.length-19));
        }
    });
    loadTable();
});
function loadTable() {
    table.render({
        elem: '#userProductList'
        , url: '/countlist' //数据接口
        , page: false //开启分页
        , toolbar: '#toolbar'
        , cols: [[
            {type: 'radio'}
            ,{field:'realname',width:'40%',title: '姓名'}
            ,{field:'pcount', width:'50%',title: '上传数量'}
        ]]
        , where: {
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val()
        }
    });
}