var app = new Vue({
    el: "#index",
    data: {
        username: "",
        permission: 1,
        loginTime: "",
        passwordVerify: "",
        passwordConfirm: "",
        emailErrorTip: "",
        emailVerifyStatus: ""
    }
});

var userInfo = {};

function saveInfo() {
    var email = $("#email").val();
    if (isEmail(email)) {
        var code = $("#email-verify-code").val();
        if (!userConfig.emailVerify || code.length === 6 || email === userInfo.email) {
            $.post("/user/basic/update", {
                avatar: "",
                realName: $("#real-name").val(),
                email: email,
                code: code
            }, function (data) {
                var json = JSON.parse(data);
                userInfo.email = json.email;
                alerts(json.message);
            })
        } else {
            alerts("验证码格式不正确");
        }
    } else {
        alerts("邮箱格式不正确");
    }
}

function updatePassword() {
    var oldPassword = $("#old-password").val();
    var newPassword = $("#new-password").val();
    var confirmNewPassword = $("#confirm-new-password").val();
    if (oldPassword && checkPassword(newPassword, confirmNewPassword)) {
        layer.load(1);
        $.post("/user/password/update", {oldPassword: oldPassword, newPassword: newPassword}, function (data) {
            layer.closeAll();
            var json = JSON.parse(data);
            if (json.status === "success") {
                alerts("密码修改成功");
                location.href = "/signin.html#login";
            } else {
                alerts(json.message);
            }
        });
    } else {
        alerts("格式不合法，无法提交");
    }
}

$(document).ready(function () {
    $.get("/user/info", function (data) {
        var json = JSON.parse(data);
        app.permission = json.permission;
        /** @namespace app.lastLoginTime */
        app.loginTime = new Date(json.lastLoginTime).format("yyyy-MM-dd hh:mm:ss");
        /** @namespace json.avator */
        if (!isEmpty(json.avator)) {
            $("#avatar").attr("src", json.avator);
        }
        app.username = json.username;
        $("#email").val(json.email);
        $("#real-name").val(json.realName);
        userInfo = json;
    });
    $(".email-verify-code").keyup(function () {
        var code = event.srcElement.value;
        if (code.length === 6) {
            $.get("/common/code/verify", {code: code}, function (data) {
                var json = JSON.parse(data);
                app.emailVerifyStatus = json.status === "success" ? "" : "验证码错误";
            });
        } else {
            app.emailVerifyStatus = "";
        }
    });
    $(".email").keyup(function () {
        var email = event.srcElement.value;
        var isChange = email !== userInfo.email;
        if (isEmail(email)) {
            if (isChange) {
                $.get("/user/email/exists", {email: email}, function (data) {
                    var json = JSON.parse(data);
                    app.emailErrorTip = json.exists ? "该邮箱已经被注册啦" : "";
                });
            }
            app.emailErrorTip = "";
        } else {
            app.emailErrorTip = "邮箱格式不正确";
        }
        $(".verify-code-div").css("display", isChange && userConfig.emailVerify ? "block" : "none");
    });
    $(".password").keyup(function () {
        var len = event.srcElement.value.length;
        if (len >= userConfig.password.minLength && len <= userConfig.password.maxLength) {
            app.passwordVerify = "";
        } else {
            app.passwordVerify = "密码长度限定为" + userConfig.password.minLength + "至" + userConfig.password.maxLength + "位";
        }
    });
    $(".confirm-password").keyup(function () {
        app.passwordConfirm = (event.srcElement.value === $("#new-password").val()) ? "" : "两次输入的密码不一样";
    });
    $(".sendVerifyCode").click(function () {
        sendVerifyCode($("#email").val(), event.srcElement);
    });
});

layer.load(1);
$.get("/config/user", function (data) {
    layer.closeAll();
    userConfig = JSON.parse(data);
});