var layer;
var table;
var upload;

layui.use(['util','table','layer','upload'], function() {
    table = layui.table;
    layer = layui.layer;
    upload = layui.upload;
    upload.render({
        elem: '#excel' //绑定元素
        ,url: '/excel' //上传接口
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx'
        ,done: function(data){
            if (data.code == 0) {
                layer.alert("上传成功", {
                        icon: 6
                    },
                    function () {
                        //关闭当前frame
                        layer.close(layer.index);
                        //可以对当前进行刷新
                        loadTable();
                    });
                return false;
            } else {
                layer.msg('上传失败，可能是某一环节出了问题');
            }
        }
        ,error: function(){
            layer.msg('上传失败，可能是某一环节出了问题');
        }
    });
    loadTable();
});
function loadTable(){
    table.render({
        elem: '#resultList'
        , url: '/resultList' //数据接口
        , page: false //开启分页
        , toolbar: '#toolbar'
        , cols: [[ //表头
            {type: 'numbers'}
            , {field: 'sum_amount', width:'7%',title: '金额$'}
            , {field: 'sum_profit', width:'7%',title: '利润￥'}
            , {field: 'onemonth_amount', width:'7%',title: '一月内金额$'}
            , {field: 'onemonth_profit', width:'7%',title: '一月内利润￥'}
            , {field: 'onethreemonth_amount', width:'7%',title: '1~3月金额$'}
            , {field: 'onethreemonth_profit', width:'7%',title: '1~3月利润￥'}
            , {field: 'threesixmonth_amount', width:'7%',title: '3~6月金额$'}
            , {field: 'threesixmonth_profit', width:'7%',title: '3~6月利润￥'}
            , {field: 'halfyearmonth_amount', width:'7%',title: '6~12月金额$'}
            , {field: 'halfyearmonth_profit', width:'7%',title: '6~12月利润￥'}
            , {field: 'yearmonth_amount', width:'7%',title: '12月前金额$'}
            , {field: 'yearmonth_profit', width:'7%',title: '12月前利润￥'}
            , {field: 'realname', width:'7%',title: '姓名'}
        ]]
    });
    table.on('toolbar(resultList)', function(obj){
        switch(obj.event){
            case 'excelR' :
                $('#excel').click();
                break;
        };
    });
}