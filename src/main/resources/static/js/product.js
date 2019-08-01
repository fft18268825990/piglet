var table;
var util;
var layer;
var laydate;
var upload;
var searchDatas;
var exportDatas;
layui.use(['util','table','layer','laydate','upload'], function() {
    table = layui.table;
    util = layui.util;
    layer = layui.layer;
    laydate = layui.laydate;
    upload = layui.upload;
    laydate.render({
        elem: '#createTimeRange'
        ,type: 'datetime'
        ,range: true
        ,done : function(value, date) {
            $('#startTime').val(value.substring(0,19));
            $('#endTime').val(value.substring(value.length-19));
        }
    });
    upload.render({
        elem: '#import' //绑定元素
        ,url: '/import' //上传接口
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
        elem: '#proList'
        ,url: '/productList' //数据接口
        ,page: true //开启分页
        ,toolbar: '#toolbar'
        ,cols: [[ //表头
            {type:'radio'}
            ,{field: 'productId', title: 'ID', width:'5%', sort: true}
            ,{field: 'sku', title: 'sku', width:'40%'}
            ,{field: 'createPerson', title: '创建人', width: '20.9%'}
            ,{field: 'createTime', title: '创建日期', width: '30%', sort: true,
                templet:function(d){
                    return util.toDateString(d.createTime);
                }}
        ]]
        ,where:{
            startTime : $('#startTime').val(),
            endTime : $('#endTime').val(),
            sku: $('#skuSearch').val()
        }
        ,done: function(){
            searchDatas={
                startTime : $('#startTime').val(),
                endTime : $('#endTime').val(),
                sku: $('#skuSearch').val()
            };

        }
    });
    table.on('toolbar(proList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
        switch(obj.event){
            case 'addPro' :
                openAdd();
                break;
            case 'editPro':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    console.log(data[0]);
                    openEdit(data[0]);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'delPro':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    delPro(data[0].productId);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'export':
                exportDataSearch();
                table.exportFile(['id','创建时间','创建人', 'sku'], exportDatas, 'xls');
                break;
            case 'importP':
                $('#import').click();
                break;
        };
    });
}
function openAdd(){
    layer.open({
        type: 2,
        area: ['600px', '200px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade:0.4,
        title: '添加产品',
        content: '/proAdd',  //url 为子布局的url路径
    });
}
function openEdit(rowdata){
    layer.open({
        type: 2,
        area: ['600px', '400px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade:0.4,
        title: '编辑产品',
        content: '/proEdit',  //url 为子布局的url路径
        success:function (layero,index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.child(rowdata);
        }
    });
}

function delPro(productId){
    layer.confirm('确认要删除吗？',function(index) {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/editPro",
            data: {'productId': productId, 'flag': 'delPro'}, // 你的formid
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

function exportDataSearch(){
    $.ajax({
        type : "Get",
        url : "/productList",
        data :searchDatas,
        async : false,
        success : function(data) {
            exportDatas = data.data;
        }
    });
}