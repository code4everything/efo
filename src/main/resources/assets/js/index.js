var app = new Vue({
    el: "#index", data: {username: "", realName: "", permission: 1, loginTime: ""}
});

var userInfo = {};

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
    })
});