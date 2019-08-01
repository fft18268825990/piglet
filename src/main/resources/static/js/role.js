var table;
var util;
var layer;

layui.use(['util','table','layer'], function() {
    table = layui.table;
    util = layui.util;
    layer = layui.layer;
    loadTable();
});



function loadTable(){
    table.render({
        elem: '#roleList'
        ,url: '/roleList' //数据接口
        ,page: true //开启分页
        ,toolbar: '#toolbar'
        ,cols: [[ //表头
            {type:'radio'}
            ,{field: 'roleId', title: 'ID', width:'5%', sort: true}
            ,{field: 'roleName', title: '角色标识', width:'20%'}
            ,{field: 'roleDes', title: '角色名称', width:'30%'}
            ,{field: 'realname', title: '创建人', width: '15.9%'}
            ,{field: 'createTime', title: '创建日期', width: '25%', sort: true,
                templet:function(d){
                    return util.toDateString(d.createTime);
                }}
        ]]
    });
    table.on('toolbar(roleList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
        switch(obj.event){
            case 'addRole' :
                openAdd();
                break;
            case 'editRole':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    openEdit(data[0]);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'roleMenu':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    menuList(data[0].roleId);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'delRole':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    delRole(data[0].roleId);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
        };
    });
}
function openAdd(){
    layer.open({
        type: 2,
        area: ['600px', '400px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade:0.4,
        title: '添加权限',
        content: '/roleAdd',  //url 为子布局的url路径
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
        title: '编辑权限',
        content: '/roleEdit',  //url 为子布局的url路径
        success:function (layero,index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.child(rowdata);
        }
    });
}

function delRole(roleId){
    layer.confirm('确认要删除吗？',function(index) {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/editRole",
            data: {'roleId': roleId, 'flag': 'delRole'}, // 你的formid
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

function roleMenu(roleId,data) {
    layer.open({
        type: 2,
        area: ['600px', '500px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade: 0.4,
        title: '分配权限',
        content: '/roleMenu',  //url 为子布局的url路径
        success: function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.child(roleId,data);
        }
    });
}

function menuList(roleId){
    $.ajax({
        cache: true,
        type: "Get",
        url: "/roleMenuIds",
        data: {'roleId': roleId}, // 你的formid
        async: false,
        error: function (request) {
            layer.alert("网络超时");
        },
        success: function (data) {
            roleMenu(roleId,data);
        }
    });
}