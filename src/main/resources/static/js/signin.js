var signinItem = new Vue({
    el: "#signin-div",
    data: {
        emailVerify: false,
        description: "",
        emailErrorTip: "",
        emailVerifyStatus: "",
        passwordVerify: "",
        passwordConfirm: ""
    }
});

function reset() {
    var email = $("#res-email").val();
    var code = $("#res-email-verify").val();
    var password = $("#res-password").val();
    var passwordConfirm = $("#res-confirm-password").val();
    var isValid = isEmail(email) && 6 === code.length && checkPassword(password, passwordConfirm);
    if (submit() && isValid) {
        layer.load(1);
        $.post("/password/reset", {email: email, code: code, password: password}, function (data) {
            layer.closeAll();
            var json = JSON.parse(data);
            if (json.status === "success") {
                alerts("密码重置成功");
                switchToLogin();
            } else {
                alerts(json.message);
            }
        });
    } else {
        alerts("格式不合法，无法提交");
    }
}

function checkPassword(password, passwordConfirm) {
    return password.length >= userConfig.password.minLength && password.length <= userConfig.password.maxLength && password === passwordConfirm;
}

function register() {
    /** @namespace globalConfig.allowRegister */
    if (globalConfig.allowRegister) {
        var username = $("#username").val();
        var email = $("#email").val();
        var verifyCode = $("#email-verify-code").val();
        var password = $("#reg-password").val();
        var passwordConfirm = $("#confirm-password").val();
        var canRegister = username.match(userConfig.usernameMatch.pattern) && (!userConfig.emailVerify || 6 === verifyCode.length) && isEmail(email) && checkPassword(password, passwordConfirm);
        if (submit() && canRegister) {
            layer.load(1);
            $.post("/register", {
                username: username,
                email: email,
                password: password,
                code: verifyCode
            }, function (data) {
                layer.closeAll();
                var json = JSON.parse(data);
                if (json.status === "success") {
                    alerts("注册成功");
                    switchToLogin();
                } else {
                    alerts(json.message);
                }
            });
        } else {
            alerts("有非法内容，无法提交");
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
    switchTo("none", "none", "block", "register", signinItem.emailVerify ? 30 : 26);
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
    signinItem.description = "";
    signinItem.emailErrorTip = "";
    signinItem.emailVerifyStatus = "";
    signinItem.passwordVerify = "";
    signinItem.passwordConfirm = "";
}

function submit() {
    return isEmpty(signinItem.description) && isEmpty(signinItem.emailErrorTip) && isEmpty(signinItem.emailVerifyStatus) && isEmpty(signinItem.passwordVerify) && isEmpty(signinItem.passwordConfirm);
}

$(document).ready(
    function () {
        $("#username").keyup(function () {
                var username = event.srcElement.value;
                if (username.match(userConfig.usernameMatch.pattern)) {
                    $.get("/username/exists", {username: username}, function (data) {
                        var json = JSON.parse(data);
                        /** @namespace json.exists */
                        if (json.exists) {
                            signinItem.description = "用户名已经存在";
                        } else {
                            signinItem.description = "";
                        }
                    });
                } else {
                    signinItem.description = userConfig.usernameMatch.description;
                }
            }
        );
        $(".email").keyup(function () {
            signinItem.emailErrorTip = isEmail(event.srcElement.value) ? "" : "邮箱格式不正确";
        });
        $(".password").keyup(function () {
            var len = event.srcElement.value.length;
            if (len >= userConfig.password.minLength && len <= userConfig.password.maxLength) {
                signinItem.passwordVerify = "";
            } else {
                signinItem.passwordVerify = "密码长度限定为" + userConfig.password.minLength + "至" + userConfig.password.maxLength + "位";
            }
        });
        $(".confirm-password").keyup(function () {
            var ele = event.srcElement;
            signinItem.passwordConfirm = (ele.value === $(ele).siblings(".password").val()) ? "" : "两次输入的密码不一样";
        });
        $(".sendVerifyCode").click(function () {
            var email = $(event.srcElement).parent().parent().siblings(".email").val();
            if (isEmail(email)) {
                var ele = event.srcElement;
                layer.load(1);
                $.get("/code/send", {email: email}, function (data) {
                    layer.closeAll();
                    var json = JSON.parse(data);
                    if (json.status === "success") {
                        layer.msg("发送成功，请前往邮箱查看");
                        $(ele).attr("disabled", "disabled");
                        $(ele).addClass("disabled");
                        setTimeout(function () {
                            $(ele).removeAttr("disabled");
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
        $(".email-verify-code").keyup(function () {
            var code = event.srcElement.value;
            if (code.length === 6) {
                $.get("/code/verify", {code: code}, function (data) {
                    var json = JSON.parse(data);
                    signinItem.emailVerifyStatus = json.status === "success" ? "" : "验证码错误";
                });
            } else {
                signinItem.emailVerifyStatus = "";
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
    signinItem.emailVerify = userConfig.emailVerify;
    /** @namespace userConfig.usernameMatch */
    if (window.location.hash === "#register") {
        switchToRegister();
    }
});