var registerItem = new Vue({
    el: "#register-div", data: {description: "用户名", emailVerify: false}
});

function register() {

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
        alerts("该网站已禁止登录，请联系管理员");
    }
}

function switchToRegister() {
    $("#login-div").css("display", "none");
    $("#reset-div").css("display", "none");
    $("#register-div").css("display", "block");
    window.location.hash = "#register";
    var heigth = registerItem.emailVerify ? "30rem" : "26rem";
    $(".center-vertical").css("height", heigth);
}

function switchToLogin() {
    $("#login-div").css("display", "block");
    $("#reset-div").css("display", "none");
    $("#register-div").css("display", "none");
    window.location.hash = "#login";
    $(".center-vertical").css("height", "21rem");
}

function switchToReset() {
    $("#login-div").css("display", "none");
    $("#reset-div").css("display", "block");
    $("#register-div").css("display", "register");
    window.location.hash = "#reset";
    $(".center-vertical").css("height", "26rem");
}

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