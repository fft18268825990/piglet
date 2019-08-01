var treetable;
var util;
var layer;
var table;
layui.config({
    base: 'module/'
}).extend({
    treetable: 'treetable-lay/treetable'
}).use(['treetable','util','layer','table'], function () {
    treetable = layui.treetable;
    util = layui.util;
    layer = layui.layer;
    table = layui.table;
    loadTreeTable();
});
function loadTreeTable(){
    treetable.render({
        treeColIndex: 2,          // treetable新增参数
        treeSpid: 7,             // treetable新增参数
        treeIdName: 'menuId',       // treetable新增参数
        treePidName: 'parentId',     // treetable新增参数
        treeDefaultClose: false,   // treetable新增参数
        treeLinkage: true,        // treetable新增参数
        elem: '#menuList',
        url: '/menuList',
        cols: [[
            {type:'radio'},
            {field: 'menuId', title: 'ID',width:'5%'},
            {field: 'menuName', title: '菜单名',width:'25%'},
            {field: 'url',title:'链接地址',width:'20.9%'},
            {field: 'icon',title:'图标代码',width:'10%'},
            {field: 'perms',title:'权限标识',width:'15%'},
            {field: 'createTime', title: '创建日期', width: '10%', sort: true,
                templet:function(d){
                    return util.toDateString(d.createTime);
                }
            },{templet: '#oper-col', title: 'oper',width:'10%'}
        ]]
    });
    table.on('tool(menuList)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'del') {
            delMenu(data.id);
        } else if (layEvent === 'edit') {
            openEdit(data);
        }
    });
}

function delMenu(menuId){
    layer.confirm('确认要删除吗？',function(index) {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/editMenu",
            data: {'menuId': menuId, 'flag': 'delMenu'}, // 你的formid
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
                            loadTreeTable();
                        });
                    return false;
                } else {
                    layer.msg(data.msg)
                }
            }
        });
    });
}
function openEdit(data){
    layer.open({
        type: 2,
        area: ['600px', '400px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade:0.4,
        title: '编辑菜单',
        content: '/menuEdit',  //url 为子布局的url路径
        success:function (layero,index) {
            var iframe = window['layui-layer-iframe' + index];
            iframe.child(data);
        }
    });
}
function openAdd(){
    layer.open({
        type: 2,
        area: ['600px', '500px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade:0.4,
        title: '添加菜单',
        content: '/menuAdd',  //url 为子布局的url路径
    });
}