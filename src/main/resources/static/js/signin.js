var registerItem = new Vue({
    el: "#register-div",
    data: {
        description: "",
        emailVerify: false,
        emailErrorTip: "",
        registerEmailVerify: "",
        registerPasswordVerify: "",
        registerPasswordConfirm: ""
    }
});

function register() {
    /** @namespace globalConfig.allowRegister */
    if (globalConfig.allowRegister) {
        var username = $("#username").val();
        var email = $("#email").val();
        var verifyCode = $("#email-verify-code").val();
        var password = $("#reg-password").val();
        var passwordConfirm = $("#confirm-password").val();
        var canRegister = username.match(userConfig.usernameMatch.pattern) && (!userConfig.emailVerify || 6 === verifyCode.length) && isEmail(email) && password.length >= userConfig.password.minLength && password.length <= userConfig.password.maxLength && password == passwordConfirm;
        if (canRegister) {

        } else {
            alerts("有非法内容，无法注册");
        }
    } else {
        alerts("该站点已禁止注册，请联系管理员");
    }
}

function login() {
    /** @namespace globalConfig.allowLogin */
    if (globalConfig.allowLogin) {
        var username = $("#loginName").val();
        var password = $("#password").val();
        // var remember = document.getElementById("remember").checked;
        if (username && password) {
            layer.load(1);
            $.post("/login", {username: username, password: password}, function (data) {
                layer.closeAll();
                var json = JSON.parse(data);
                if (json.status === "success") {
                    window.location.href = "index.html";
                } else {
                    alerts("登录失败，用户名或密码不正确");
                }
            });
        } else {
            alerts("用户名或密码不能为空");
        }
    } else {
        alerts("该站点已禁止登录，请联系管理员");
    }
}

function switchToRegister() {
    switchTo("none", "none", "block", "register", registerItem.emailVerify ? 30 : 26);
}

function switchToLogin() {
    switchTo("block", "none", "none", "login", 21);
}

function switchToReset() {
    switchTo("none", "block", "none", "reset", 26);
}

function switchTo(login, reset, register, hash, height) {
    $("#login-div").css("display", login);
    $("#reset-div").css("display", reset);
    $("#register-div").css("display", register);
    window.location.hash = "#" + hash;
    $(".center-vertical").css("height", height + "rem");
}

$(document).ready(
    function () {
        $("#username").keyup(function () {
                if (event.srcElement.value.match(userConfig.usernameMatch.pattern)) {
                    registerItem.description = "";
                } else {
                    registerItem.description = userConfig.usernameMatch.description;
                }
            }
        );
        $("#email").keyup(function () {
            registerItem.emailErrorTip = isEmail(event.srcElement.value) ? "" : "邮箱格式不正确";
        });
        $("#reg-password").keyup(function () {
            var len = event.srcElement.value.length;
            if (len >= userConfig.password.minLength && len <= userConfig.password.maxLength) {
                registerItem.registerPasswordVerify = "";
            } else {
                registerItem.registerPasswordVerify = "密码长度限定为" + userConfig.password.minLength + "至" + userConfig.password.maxLength + "位";
            }
        });
        $("#confirm-password").keyup(function () {
            registerItem.registerPasswordConfirm = (event.srcElement.value === $("#reg-password").val()) ? "" : "两次输入的密码不一样";
        });
        $(".sendVerifyCode").click(function () {
            var email = $("#email").val();
            if (isEmail(email)) {
                var ele = event.srcElement;
                layer.load(1);
                $.get("/verifyCode", {email: email}, function (data) {
                    layer.closeAll();
                    var json = JSON.parse(data);
                    if (json.status === "success") {
                        layer.msg("发送成功，请前往邮箱查看");
                        $(ele).addClass("disabled");
                        setTimeout(function () {
                            $(ele).removeClass("disabled");
                        }, 60000);
                    } else {
                        alerts("获取验证码失败，请联系管理员");
                    }
                });
            } else {
                alerts("邮箱格式不合法");
            }
        });
    }
);

switch (window.location.hash) {
    case "#register":
        switchToRegister();
        break;
    case "#reset":
        switchToReset();
        break;
    default:
        switchToLogin();
        break;
}

var userConfig = {};

layer.load(1);
$.get("/config/user", function (data) {
    layer.closeAll();
    userConfig = JSON.parse(data);
    registerItem.emailVerify = userConfig.emailVerify;
    /** @namespace userConfig.usernameMatch */
    if (window.location.hash === "#register") {
        switchToRegister();
    }
});