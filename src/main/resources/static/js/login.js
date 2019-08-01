var layer;

layui.use('layer', function(){
    layer = layui.layer;
    $("#login").on('click',function(){$("#signupForm").submit();});

    $(document).on('keydown',function(){
        if(event.keyCode==13){
            $("#signupForm").submit();
        }
    });

    validateRule();
});





$.validator.setDefaults({
    submitHandler: function () {
        login();
    }
});

function login(){
    console.log($('#signupForm').serialize());
    $.ajax({
        type: "POST",
        url: "/checklogin",
        data: $('#signupForm').serialize(),
        success: function (r) {
            if (r.code == 0) {
                layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                parent.location.href = '/index';
            } else {
                layer.msg(r.msg);
            }
        }
    });
}

function validateRule(){
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名"
            },
            password: {
                required: icon + "请输入您的密码"
            }
        }
    })
}