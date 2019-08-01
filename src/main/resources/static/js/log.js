var layer;
var table;
var util;

layui.use(['util','table','layer','upload'], function() {
    table = layui.table;
    util = layui.util;
    layer = layui.layer;
    loadTable();
});
function loadTable(){
    table.render({
        elem: '#logList'
        , url: '/logList' //数据接口
        , page: true //开启分页
        , toolbar: '#toolbar'
        , cols: [[ //表头
            {type: 'radio'}
            , {field: 'logId',width:'2%',title: 'id'}
            , {field: 'content', width:'72%',title: '结果'}
            , {field: 'fileName', width:'5%',title: '文件名'}
            , {field: 'realname', width:'5%',title: '上传人'}
            ,{field: 'createTime', title: '上传日期', width: '10%', sort: true,
                templet:function(d){
                    return util.toDateString(d.createTime);
                }}
        ]]
    });
    table.on('toolbar(logList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
        switch(obj.event){
            case 'delLog':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    delLog(data[0].logId);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
        };
    });
}
function delLog(logId){
    layer.confirm('确认要删除吗？',function(index) {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/editLog",
            data: {'logId': logId, 'flag': 'delLog'}, // 你的formid
            async: false,
            error: function (request) {
                layer.alert("网络超时");
            },
            success: function (data) {
                if (data.code == 0) {
                    layer.alert("删除成功", {
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
                    layer.msg(data.msg)
                }
            }
        });
    });
}