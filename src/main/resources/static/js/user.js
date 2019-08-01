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
        elem: '#userList'
        ,url: '/userList' //数据接口
        ,page: true //开启分页
        ,toolbar: '#toolbar'
        ,cols: [[ //表头
            {type:'radio'}
            ,{field: 'userId', title: 'ID', width:'5%', sort: true}
            ,{field: 'username', title: '用户名', width:'20%'}
            ,{field: 'realname', title: '姓名', width:'20%'}
            ,{field: 'phone', title: '手机', width:'15.9%'}
            ,{field: 'state', title: '状态', width: '10%',
                templet:function(d){
                    if(d.state == '1'){
                        return '<span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span></td>'
                    } else {
                        return '<span class="layui-btn layui-btn-normal layui-btn-mini layui-btn-disabled">已禁用</span>'
                    }
                }
            }
            ,{field: 'createTime', title: '创建日期', width: '25%', sort: true,
                templet:function(d){
                    return util.toDateString(d.createTime);
                }
            }
        ]]
    });
    table.on('toolbar(userList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
        switch(obj.event){
            case 'addUser' :
                openAdd();
                break;
            case 'editUser':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    openEdit(data[0]);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'banUser':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    banUser(data[0]);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'delUser':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    delUser(data[0].userId);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'userRole':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    roleList(data[0].userId);
                }else{
                    layer.msg('请选择一行数据');
                }
                break;
            case 'editPwd':
                var data = checkStatus.data;  //获取选中行数据
                if(data.length>0){
                    editPwd(data[0].userId,data[0].password);
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
        title: '添加用户',
        content: '/adminAdd',  //url 为子布局的url路径
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
        title: '编辑用户',
        content: '/adminEdit',  //url 为子布局的url路径
        success:function (layero,index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.child(rowdata);
        }
    });
}

function banUser(rowdata){
    layer.confirm('确认要启用/禁用吗？',function(index) {
        var state = (rowdata.state == '1') ? 0 : 1;
        $.ajax({
            cache: true,
            type: "POST",
            url: "/editUser",
            data: {'userId': rowdata.userId, 'state': state, 'flag': 'banUser'}, // 你的formid
            async: false,
            error: function (request) {
                layer.alert("网络超时");
            },
            success: function (data) {
                if (data.code == 0) {
                    layer.alert("操作成功", {
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

function delUser(userId){
    layer.confirm('确认要删除吗？',function(index) {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/editUser",
            data: {'userId': userId, 'flag': 'delUser'}, // 你的formid
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

function userRole(userId,roleList) {
    layer.open({
        type: 2,
        area: ['600px', '200px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade: 0.4,
        title: '分配权限',
        content: '/userRole',  //url 为子布局的url路径
        success: function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.child(userId,roleList);
        }
    });
}

function roleList(userId){
    $.ajax({
        cache: true,
        type: "POST",
        url: "/userRole",
        data: {'userId': userId}, // 你的formid
        async: false,
        error: function (request) {
            layer.alert("网络超时");
        },
        success: function (data) {
            userRole(userId,data);
        }
    });
}

function editPwd(userId,oldPwd){
    layer.open({
        type: 2,
        area: ['600px', '400px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade: 0.4,
        title: '修改密码',
        content: '/editPwd',  //url 为子布局的url路径
        success: function (layero, index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.child(userId,oldPwd);
        }
    });
}