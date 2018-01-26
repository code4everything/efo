var app = new Vue({
    el: "#index",
    data: {
        username: "",
        realName: "",
        permission: 1,
        loginTime: "",
        passwordVerify: "",
        passwordConfirm: "",
        emailVerify: "",
        emailErrorTip: ""
    }
});

var userInfo = {};

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
        userInfo = json;
    });
    $(".email").keyup(function () {
        app.emailErrorTip = isEmail(event.srcElement.value) ? "" : "邮箱格式不正确";
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
});

layer.load(1);
$.get("/config/user", function (data) {
    layer.closeAll();
    userConfig = JSON.parse(data);
    app.emailVerify = userConfig.emailVerify;
});