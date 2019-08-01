var layer;

layui.use('layer', function(){
    layer = layui.layer;
});

$(function(){
    connect();
    $('#send').on('click',function(){
        if($('#message').val() == '' || $('#message').val() == null){
            layer.msg('发送的消息不能为空');
            return;
        }
        socket.emit('sendMessage',{'loginUserNum':user.userId,'loginUserName':user.realname,'content':$('#message').val()});
        $('#message').val('');
    });

    $(document).on('keydown',function(){
        if(event.keyCode==13){
            $('#send').click();
        }
    });
});

function connect() {
    var loginUserNum = user.userId;
    var opts = {
        query: 'loginUserNum=' + loginUserNum
    };
    socket = io.connect('ws://192.168.1.105:8089', opts);
    socket.on('connect', function () {
        //console.log(user.realname+"连接成功");
        //serverOutput('<div>'+user.realname+'连接成功</div>');
    });
    socket.on('push_event', function (data) {
        output('<span>' + data + ' </span>');
        console.log(data);
    });

    socket.on('disconnect', function () {
        //serverOutput('<span>' + '已下线! </span>');
    });

    socket.on('on_line', function (data) {
        serverOutput('<div>'+data.content+'</div></br>');
    });

    socket.on('off_line', function (data) {
        serverOutput('<div>'+data.content+'</div></br>');
    });

    socket.on('sendMessage', function (data) {
        serverOutput('<div>'+data.loginUserName+'   ('+data.sendDate+')</div><div>'+data.content+'</div>');
        var sysMessage = document.getElementById('sysMessage');
        sysMessage.scrollTop = sysMessage.scrollHeight;
    });
}

function output(message) {
    $('#sysMessage').append(message);
}

function serverOutput(message) {
    $('#sysMessage').append(message);
}