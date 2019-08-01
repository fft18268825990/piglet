var layer;

layui.use('layer', function(){
    layer = layui.layer;
    leftMenu();
});


function leftMenu(){
    $.ajax({
        type : "GET",
        async:true,
        url : "/leftMenuTree" ,
        error : function(request) {
            layer.alert("网络超时");
        },
        success : function(data) {
            renderMenu(data);
            $('.layui-tab-title li[lay-id]').find('.layui-tab-close').click();
            $('#tab_right').hide();
            $('#tab_show').hide();
        }
    });
}

function renderMenu(tree){
    var menuStr = "";
    $(tree).each(function(index,menu){
        menuStr += '<li>';
        menuStr += '<a href="javascript:;">';
        menuStr += '<i class="iconfont left-nav-li" lay-tips="'+menu.title+'">'+menu.attributes.icon+'</i>';
        menuStr += '<cite>'+menu.title+'</cite>';
        menuStr += '<i class="iconfont nav_right">&#xe697;</i></a>';
        menuStr += '<ul class="sub-menu">';
        $(menu.children).each(function(index2,menu2){
            menuStr += '<li>';
            menuStr += '<a onclick="xadmin.add_tab(\''+menu2.title+'\',\''+menu2.attributes.url+'\')">';
            menuStr += '<i class="iconfont">&#xe6a7;</i>';
            menuStr += '<cite>'+menu2.title+'</cite></a>';
            menuStr += '</li>';
        });
        menuStr += '</ul>';
        menuStr += '</li>';
    });
    $("#nav").html(menuStr);
}

function openInformation(){
    layer.open({
        type: 2,
        area: ['600px', '400px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: false,
        shade:0.4,
        title: '个人信息',
        content: '/userInfo',  //url 为子布局的url路径
    });
}