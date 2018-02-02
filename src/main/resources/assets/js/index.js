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

/**
 * 保存用户信息
 */
function saveInfo() {
    var email = $("#email").val();
    if (isEmail(email)) {
        var code = $("#email-verify-code").val();
        if (!userConfig.emailVerify || code.length === 6 || email === userInfo.email) {
            layer.load(1);
            $.ajax({
                url: '/user/basic/update',
                type: 'PUT',
                dataType: "",
                data: {
                    avatar: $("#avatar").attr("src"),
                    realName: $("#real-name").val(),
                    email: email,
                    code: code
                },
                success: function (data) {
                    layer.closeAll();
                    var json = JSON.parse(data);
                    userInfo.email = json.email;
                    alerts(json.message);
                }
            });
        } else {
            alerts("验证码格式不正确");
        }
    } else {
        alerts("邮箱格式不正确");
    }
}

/**
 * 更新密码
 */
function updatePassword() {
    var oldPassword = $("#old-password").val();
    var newPassword = $("#new-password").val();
    var confirmNewPassword = $("#confirm-new-password").val();
    if (oldPassword && checkPassword(newPassword, confirmNewPassword)) {
        layer.load(1);
        $.ajax({
            url: "/user/password/update",
            type: "PUT",
            data: {oldPassword: oldPassword, newPassword: newPassword},
            success: function (data) {
                layer.closeAll();
                var json = JSON.parse(data);
                if (json.status === "success") {
                    alerts("密码修改成功");
                    location.href = "/signin.html#login";
                } else {
                    alerts(json.message);
                }
            }
        });
    } else {
        alerts("格式不合法，无法提交");
    }
}

function getUserInfo() {
    layer.load(1);
    $.get("/user/info", function (data) {
        layer.closeAll();
        var json = JSON.parse(data);
        userInfo = json;
        app.permission = json.permission;
        /** @namespace app.lastLoginTime */
        app.loginTime = new Date(json.lastLoginTime).format("yyyy-MM-dd hh:mm:ss");
        /** @namespace json.avator */
        if (!isEmpty(json.avatar)) {
            $("#avatar").attr("src", json.avatar);
        }
        app.username = json.username;
        $("#email").val(json.email);
        $("#real-name").val(json.realName);
        checkEmailChange(json.email);
    });
}

function showAvatarModal() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        offset: 'ct',
        shadeClose: true,
        content: "<input id='file-input' class='form-control file' multiple data-max-file-count='100' data-preview-file-type='img' name='file' type='file'/>"
    });
    $("#file-input").fileinput({
        uploadUrl: "/common/avatar/upload",
        uploadAsync: true,
        maxFileCount: 1,
        maxFilePreviewSize: 10485760
    }).on('fileuploaded', function (event, data, previewId, index) {
        var json = data.response;
        if (JSON.stringify(json).indexOf("success") > 0) {
            $("#avatar").attr("src", json.success);
        } else {
            alerts(json.error);
        }
    });
}

$(document).ready(function () {
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
        checkEmailChange(event.srcElement.value);
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
    getResource();
    $(".nav-link").click(function () {
        var href = $(event.srcElement).attr("href");
        if (href.startsWith("resource", 1)) {
            getResource();
        } else if (href.startsWith("uploaded", 1)) {
            getUserUploaded();
        } else if (href.startsWith("downloaded", 1)) {
            getUserDownloaded();
        } else if (href.startsWith("bio", 1)) {
            getUserInfo();
        }
    });
});

function checkEmailChange(email) {
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
}

/**
 * 加载用户配置信息
 */
layer.load(1);
$.get("/config/user", function (data) {
    layer.closeAll();
    userConfig = JSON.parse(data);
});

function getUserDownloaded() {
    layer.load(1);
    $.get("/file/user/downloaded", function (data) {
        layer.closeAll();
        setResources(JSON.parse(data), "#downloaded-content");
    });
}

function getUserUploaded() {
    layer.load(1);
    $.get("/file/user/uploaded", function (data) {
        layer.closeAll();
        setResources(JSON.parse(data), "#uploaded-content");
    });
}

function getResource() {
    layer.load(1);
    $.get("/file/all", function (data) {
        layer.closeAll();
        setResources(JSON.parse(data), "#resources-content");
    });
}

function setResources(resources, tabId) {
    var contentHtml = "";
    $.each(resources, function (i, resource) {
        /** @namespace resource.fileName */
        /** @namespace resource.createTime */
        /** @namespace resource.categoryName */
        /** @namespace resource.checkTimes */
        /** @namespace resource.downloadTimes */
        /** @namespace resource.visitUrl */
        contentHtml += "<div class='row content-box rounded'><div class='col-12 col-sm-12'><br/><div class='row'>" +
            "<div class='col-sm-1 col-0'><img src='" + (resource.avatar ? resource.avatar : "/assets/img/default-user.jpg") + "' class='rounded avatar'/>" +
            "</div><div class='col-sm-11 col-12'><h4><a data-toggle='tooltip' href='" + resource.visitUrl + "' target='_blank' title='" + resource.description + "'>" + resource.fileName + "</a>" +
            "</h4><p>上传者：<b>" + resource.username + "</b>&emsp;上传时间：<b>" + new Date(resource.createTime).format("yyyy-MM-dd hh:mm:ss") + "</b>&emsp;文件大小：<b>" + formatSize(resource.size) + "</b>&emsp;分类：<b>" + resource.categoryName + "</b>" +
            "&emsp;标签：<b>" + resource.tag + "</b>&emsp;查看次数：<b>" + resource.checkTimes + "</b>&emsp;下载次数：<b>" + resource.downloadTimes + "</b>" +
            "</p></div></div><br/></div></div><br/>";
    });
    $(tabId).html(contentHtml);
    $('[data-toggle="tooltip"]').tooltip();
    setCSS();
}