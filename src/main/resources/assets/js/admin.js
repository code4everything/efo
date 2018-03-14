var app = new Vue({
    el: "#container",
    data: {
        categories: [],
        downloaded: [],
        uploaded: [],
        files: [],
        serverFiles: [],
        selectedServerFiles: [],
        auths: [],
        users: []
    }
});

var authFileSearch = "";

var authSearchOffset = 0;

var authModal = new Vue({
    el: "#authAddedModal", methods: {
        searchFileInAuth: function () {
            var file = $("#auth-file-search").val();
            if (isEmpty(file)) {
                alerts("内容不能为空");
            } else {
                if (file === authFileSearch) {
                    authSearchOffset += 1;
                } else {
                    authSearchOffset = 0;
                    authFileSearch = file;
                }
                layer.load(1);
                $.get("/file/basic/all", {
                    user: "",
                    file: file,
                    category: "",
                    offset: authSearchOffset
                }, function (data) {
                    layer.closeAll();
                    var json = JSON.parse(data);
                    if (json.length < 1) {
                        alerts("糟糕，没有数据了");
                    } else {
                        var ele = $("#auth-file-list-group");
                        $(ele).empty();
                        $.each(json, function (i, n) {
                            var li = "<li class='list-group-item list-group-item-success'><a href='javascript:' class='auth-file' onclick='$(event.srcElement).parent().remove();' data-key='" + n.id + "'>" + n.localUrl + "</a></li>";
                            $(ele).append(li);
                        });
                        $('[data-toggle="tooltip"]').tooltip();
                    }
                });
            }
        },
        "searchUserInAuth": function () {
            var user = $("#auth-user-search").val();
            if (isEmpty(user)) {
                alerts("内容不能为空");
            } else {
                layer.load(1);
                $.get("/user/all", {
                    user: user,
                    offset: 0
                }, function (data) {
                    layer.closeAll();
                    var json = JSON.parse(data);
                    if (json.length < 1) {
                        alerts("糟糕，没有数据了");
                    } else {
                        var ele = $("#auth-user-list-group");
                        $(ele).empty();
                        $.each(json, function (i, n) {
                            var li = "<li class='list-group-item list-group-item-success'><a href='javascript:' class='auth-file' onclick='$(event.srcElement).parent().remove();' data-key='" + n.id + "'>" + n.username + "</a></li>";
                            $(ele).append(li);
                        });
                        $('[data-toggle="tooltip"]').tooltip();
                    }
                });
            }
        }
    }
});

var serverFileSearchHistory = [];

Vue.component('file-filter-item', {
    template: '<div class="col-12 col-sm-10 offset-sm-1 content-box rounded">' +
    '            <br/>' +
    '            <div class="row">' +
    '                <div class="col-sm-2 col-4">' +
    '                    <input type="text" title="指定分类" data-toggle="tooltip" class="form-control file-filter category-filter"' +
    '                           placeholder="分类名称" onkeyup="fileFilter();"/>' +
    '                </div>' +
    '                <div class="col-sm-3 col-4">' +
    '                    <input type="text" title="指定用户" data-toggle="tooltip" class="form-control file-filter user-filter"' +
    '                           placeholder="用户名或邮箱" onkeyup="fileFilter();"/>' +
    '                </div>' +
    '                <div class="col-sm-4 col-4">' +
    '                    <input type="text" title="指定文件" data-toggle="tooltip" class="form-control file-filter file-name-filter"' +
    '                           placeholder="文件名" onkeyup="fileFilter();"/>' +
    '                </div>' +
    '            </div>' +
    '            <br/>' +
    '        </div>'
});

var offset = 0;

var enableSearch = false;

function getTabInfo(tabId) {
    offset = 0;
    enableSearch = false;
    changeTabInfo(tabId);
}

function changeTabInfo(tabId) {
    if (tabId.indexOf("#") === 0) {
        tabId = tabId.substr(1);
    }
    if (tabId === "upload-manager") {
        getUploaded();
    } else if (tabId === "download-manager") {
        getDownloaded();
    } else if (tabId === "file-manager") {
        getFile();
    } else if (tabId === "auth-manager") {
        getAuth();
    } else if (tabId === "category-manager") {
        setCategoryToDefault();
        getCategory();
    } else if (tabId === "user-manager") {
        getUser();
    } else if (tabId === "admin-manager") {

    } else if (tabId === "system-setting") {
        getConfig();
    } else {
        alerts("没有找到可执行的方法");
    }
    setTimeout(function () {
        $('[data-toggle="tooltip"]').tooltip();
        setCSS();
    }, 1000);
}

function getConfig() {
    layer.load(1);
    $.get("/config/all", function (data) {
        layer.closeAll();
        $("#json-editor").children("textarea").val(JSON.stringify(JSON.parse(data), undefined, 4));
    });
}

function saveConfig() {
    layer.load(1);
    $.ajax({
        url: "/config",
        type: "PUT",
        data: {config: $("#json-editor").children("textarea").val()},
        success: function (data) {
            layer.closeAll();
            alerts(JSON.parse(data).message);
        }
    });
}

// var editor = new JSONEditor(document.getElementById("json-editor"), {
//     theme: 'foundation5'
// });

function getUser() {
    layer.load(1);
    $.get("/user/all", getFileFilterParameters(), function (data) {
        layer.closeAll();
        var json = JSON.parse(data);
        if (json.length < 1) {
            alerts("糟糕，没有数据了");
        } else if (offset < 1) {
            app.users = json;
        } else {
            app.users = app.users.concat(json);
        }
    });
}

function getIconYesOrNo(flag) {
    return "<i class='text-" + (flag > 0 ? "success" : "danger") + " glyphicon glyphicon-" + (flag > 0 ? "ok" : "remove") + "'></i>";
}

function getAuth() {
    layer.load(1);
    $.get("/auth/all", getFileFilterParameters(), function (data) {
        layer.closeAll();
        var json = JSON.parse(data);
        if (json.length < 1) {
            alerts("糟糕，没有数据了");
        } else if (offset < 1) {
            app.auths = json;
        } else {
            app.auths = app.auths.concat(json);
        }
    });
}

var fileFilterDivParent;

function getFile() {
    layer.load(1);
    $.get("/file/basic/all", getFileFilterParameters(), function (data) {
        layer.closeAll();
        var json = JSON.parse(data);
        if (json.length < 1) {
            alerts("糟糕，没有数据了");
        } else if (offset < 1) {
            app.files = json;
        } else {
            app.files = app.files.concat(json);
        }
    });
}

function getFileFilterParameters() {
    var res = {
        user: enableSearch ? $(fileFilterDivParent).find(".user-filter").val() : "",
        file: enableSearch ? $(fileFilterDivParent).find(".file-name-filter").val() : "",
        category: enableSearch ? $(fileFilterDivParent).find(".category-filter").val() : "",
        offset: offset
    };
    enableSearch = false;
    return res;
}

function fileFilter() {
    /** @namespace window.event.keyCode */
    if (window.event.keyCode === 13) {
        offset = 0;
        enableSearch = true;
        fileFilterDivParent = $(event.srcElement).parent().parent();
        changeTabInfo(window.location.hash);
    }
}

function getUploaded() {
    layer.load(1);
    $.get("/uploaded/all", getFileFilterParameters(), function (data) {
        layer.closeAll();
        var json = JSON.parse(data);
        if (json.length < 1) {
            alerts("糟糕，没有数据了");
        } else if (offset < 1) {
            app.uploaded = json;
        } else {
            app.uploaded = app.uploaded.concat(json);
        }
    });
}

function getDownloaded() {
    layer.load(1);
    $.get("/downloaded/all", getFileFilterParameters(), function (data) {
        layer.closeAll();
        var json = JSON.parse(data);
        if (json.length < 1) {
            alerts("糟糕，没有数据了");
        } else if (offset < 1) {
            app.downloaded = json;
        } else {
            app.downloaded = app.downloaded.concat(json);
        }
    });
}

function getCategory() {
    $.get("/category/all", function (data) {
        app.categories = JSON.parse(data);
    });
}

function deleteCategory() {
    var srcTr = $(event.srcElement).parents("tr");
    var categoryId = $(srcTr).children("td.category-id").text();
    $.ajax({
        url: "/category/" + categoryId, type: "DELETE", success: function (data) {
            var json = JSON.parse(data);
            if (json.status === "success") {
                app.categories.splice($(srcTr).children("td.hide").text(), 1);
                alerts("删除成功");
            } else {
                alerts("删除失败，请稍后重新尝试");
            }
        }, error: function () {
            alerts("删除失败，该分类已被多个文件引用，无法删除（可尝试修改文件分类或删除文件）。");
        }
    });
}

function showFileShareModal() {
    if (isEmpty($("#select-url").val())) {
        getServerFileByPath(false);
    }
    var modal = $("#fileAddedModal");
    var ele = $(modal).find(".modal-content");
    if (isMobile()) {
        $(ele).removeClass("width-60-vm");
    } else {
        $(ele).addClass("width-60-vm");
    }
    $(modal).modal("show");
}

var selectedRows = [];

var rowIndex;

/**
 * 演示同类型的模态框
 * @param table tableId
 * @param modal 模态框
 */
function showFileModal(table, modal) {
    rowIndex = 0;
    selectedRows = getSelectedRows($(table).children("tbody"));
    if (selectedRows.length < 1) {
        alerts("请至少选择一行");
    } else {
        if (modal === "#fileModifiedModal") {
            setModifyFile();
        } else if (modal === "#fileAuthModal") {
            setFileAuth();
        } else if (modal === "#authEditModal") {
            setAuth();
        } else if (modal === "#userFileAuthModal") {
            setUserFileAuth();
        } else if (modal === "#userPasswordModal") {
            setUsername(modal, '');
        } else if (modal === "#userAuthModal") {
            setUsername(modal, "auth");
        }
        $(modal).modal("show");
    }
}

function setFileAuth() {
    checkRowIndex();
    var file = app.files[$(selectedRows[rowIndex]).children(".file-index").attr("data-key")];
    /** @namespace file.localUrl */
    $("#file-local-url").val(file.localUrl);
    $("#file-auth-id").val(file.id);
    layer.load(1);
    $.get("/file/" + file.id + "/auth", function (data) {
        layer.closeAll();
        var json = JSON.parse(data);
        /** @namespace json.isDownloadable */
        $("#file-downloadable-auth").val(json.isDownloadable);
        /** @namespace json.isDeletable */
        $("#file-deletable-auth").val(json.isDeletable);
        /** @namespace json.isUpdatable */
        $("#file-updatable-auth").val(json.isUpdatable);
        /** @namespace json.isVisible */
        $("#file-visible-auth").val(json.isVisible);
    });
}

function setUsername(id, auth) {
    checkRowIndex();
    var user = app.users[$(selectedRows[rowIndex]).children(".user-index").attr("data-key")];
    var parent = $(id);
    $(parent).find(".username").val(user.username);
    if (!isEmpty(auth)) {
        $(parent).find(".user-permission").val(user.permission);
    }
}

function setUserFileAuth() {
    checkRowIndex();
    var user = app.users[$(selectedRows[rowIndex]).children(".user-index").attr("data-key")];
    var parent = $("#userFileAuthModal");
    $(parent).find(".username").val(user.username);
    $(parent).find(".user-downloadable").val(user.isDownloadable);
    /** @namespace user.isUploadable */
    $(parent).find(".user-uploadable").val(user.isUploadable);
    $(parent).find(".user-deletable").val(user.isDeletable);
    $(parent).find(".user-updatable").val(user.isUpdatable);
}

function setAuth() {
    checkRowIndex();
    var auth = app.auths[$(selectedRows[rowIndex]).children(".auth-index").attr("data-key")];
    $("#auth-username").val(auth.username);
    $("#auth-file-local-url").val(auth.localUrl);
    $("#auth-id").val(auth.id);
    $("#auth-downloadable").val(auth.isDownloadable);
    $("#auth-deletable").val(auth.isDeletable);
    $("#auth-updatable").val(auth.isUpdatable);
    $("#auth-visible").val(auth.isVisible);
}

function checkRowIndex() {
    if (rowIndex < 0) {
        rowIndex = selectedRows.length - 1;
    } else if (rowIndex >= selectedRows.length) {
        rowIndex = 0;
    }
}

function toggleCheckBoxStatus(tab) {
    setCheckboxesStatus($("#" + tab + "-manager-table").children("tbody"), document.getElementById(tab + "-toggle-box").checked);
}

function setModifyFile() {
    checkRowIndex();
    var file = app.files[$(selectedRows[rowIndex]).children(".file-index").attr("data-key")];
    /** @namespace file.localUrl */
    $("#old-file-local-url").val(file.localUrl);
    $("#old-file-visit-url").val(file.visitUrl);
    $("#file-id").val(file.id);
}

function getServerFileByPath(addTo) {
    var path = $("#select-url").val();
    serverFileSearchHistory = serverFileSearchHistory.concat(path);
    layer.load(1);
    $.get("/file/server", {path: path}, function (data) {
        layer.closeAll();
        app.serverFiles = JSON.parse(data);
        var ele = $("#server-file-list-group");
        $(ele).empty();
        $.each(app.serverFiles, function (i, json) {
            var li = "<li class='list-group-item list-group-item-" + (json.isDirectory ? "primary" : "info") + "' data-key='" + i + "'><a href='javascript:' class='server-file' title='" + json.absolutePath + "' data-toggle='tooltip' onclick='selectServerFile();'>" + json.name + "</a></li>";
            $(ele).append(li);
            if (addTo && json.isFile) {
                addToSelectedServerFile(json);
            }
        });
        $('[data-toggle="tooltip"]').tooltip();
    });
}

function editCategory() {
    $("#category-title").text("编辑分类");
    var srcTr = $(event.srcElement).parents("tr");
    var categoryId = $(srcTr).children("td.category-id").text();
    $("#category-id").val(categoryId);
    $("#category-key").val($(srcTr).children("td.hide").text());
    $("#category-name").val($(srcTr).children("td.category-name").text());
}

function saveCategory() {
    var name = $("#category-name").val();
    if (isEmpty(name)) {
        alerts("分类名不能为空");
    } else {
        var id = $("#category-id").val();
        layer.load(1);
        if (id > 0) {
            $.ajax({
                url: "/category/" + id, type: "PUT", data: {name: name}, success: function (data) {
                    if (data.indexOf("success") > 0) {
                        app.categories[$("#category-key").val()].name = name;
                    }
                    responseTip(data);
                }
            });
        } else {
            $.post("/category/" + name, function (data) {
                if (data.indexOf("success") > 0) {
                    getCategory();
                }
                responseTip(data);
            });
        }
    }
    setCategoryToDefault();
}

function toggleRowSelectedStatus(ele) {
    if (event.srcElement.toString() === "[object HTMLTableCellElement]") {
        var cb = $(ele).find("input[type='checkbox']")[0];
        cb.checked = !cb.checked;
    }
}

function setCategoryToDefault() {
    $("#category-id").val(0);
    $("#category-key").val(0);
    $("#category-name").val("");
    $("#category-title").text("添加新分类");
}

function addToSelectedServerFile(json) {
    app.selectedServerFiles = app.selectedServerFiles.concat(json);
    var li = "<li class='list-group-item list-group-item-success' data-key='" + (app.selectedServerFiles.length - 1) + "'><a href='javascript:' class='selected-server-file' title='" + json.absolutePath + "' data-toggle='tooltip' onclick='removeSelectedServerFile();'>" + json.name + "</a></li>";
    $("#selected-file-list-group").append(li);
    $('[data-toggle="tooltip"]').tooltip();
}

function selectServerFile() {
    var json = app.serverFiles[$(event.srcElement).parent().attr("data-key")];
    if (json.isDirectory) {
        /** @namespace json.absolutePath */
        $("#select-url").val(json.absolutePath);
        getServerFileByPath(document.getElementById("share-all-file").checked);
    } else {
        addToSelectedServerFile(json);
    }
}

function removeSelectedServerFile() {
    var liEle = $(event.srcElement).parent();
    var key = $(liEle).attr("data-key");
    app.selectedServerFiles.splice(key, 1);
    $(liEle).remove();
}

function toOneOrZero(val) {
    return val > 0 ? 1 : 0;
}

function updateAuth(url, down, uplo, dele, upda, visi, key) {
    var auth = toOneOrZero(down) + "," + toOneOrZero(uplo) + "," + toOneOrZero(dele) + "," + toOneOrZero(upda) + "," + toOneOrZero(visi);
    layer.load(1);
    $.ajax({
        url: url, type: "PUT", data: {auth: auth}, success: function (data) {
            layer.closeAll();
            var result = data.indexOf("success") > 0;
            alerts("更新" + boolToChinese(result));
            if (result) {
                if (url.indexOf("auth") === 1) {
                    app.auths[key].isDownloadable = down;
                    app.auths[key].isDeletable = dele;
                    app.auths[key].isUpdatable = upda;
                    app.auths[key].isVisible = visi;
                } else if (url.indexOf("user") === 1) {
                    app.users[key].isDownloadable = down;
                    app.users[key].isUploadable = uplo;
                    app.users[key].isDeletable = dele;
                    app.users[key].isUpdatable = upda;
                }
            }
        }
    });
}

function doDelete(table, json, index, url) {
    selectedRows = getSelectedRows($(table).children("tbody"));
    if (selectedRows.length < 1) {
        alerts("请至少选中一行");
    } else {
        layer.confirm('是否确定删除选中的所有行', {
            btn: ['确定', '取消']
        }, function () {
            var ids = "";
            selectedRows.forEach(function (tr) {
                ids += json[$(tr).children(index).attr("data-key")].id + ",";
            });
            ids = ids.substr(0, ids.length - 1);
            layer.load(1);
            $.ajax({
                url: url + ids, type: "DELETE", success: function (data) {
                    layer.closeAll();
                    var json = JSON.parse(data);
                    if (json.status === "success") {
                        selectedRows.forEach(function (tr) {
                            var key = $(tr).children(index).attr("data-key");
                            if (table.indexOf("file") > 0) {
                                app.files.splice(key, 1);
                            } else if (table.indexOf("auth") > 0) {
                                app.auths.splice(key, 1);
                            }
                        });
                        $("[data-toggle='tooltip']").tooltip();
                        layer.msg("删除成功");
                    } else {
                        alerts("删除失败");
                    }
                }
            })
        }, function () {
            layer.msg("取消了操作");
        });
    }
}

$(document).ready(function () {
    $("#auth-add-button").click(function () {
        var fileList = $("#auth-file-list-group").find("a");
        var files = "";
        for (var i = 0; i < fileList.length; i++) {
            files += $(fileList[i]).attr("data-key") + ",";
        }
        var userList = $("#auth-user-list-group").find("a");
        var users = "";
        for (var i = 0; i < userList.length; i++) {
            users += $(userList[i]).attr("data-key") + ",";
        }
        if (isEmpty(files) || isEmpty(users)) {
            alerts("内容不能为空");
        } else {
            files = files.substr(0, files.length - 1);
            users = users.substr(0, users.length - 1);
            var down = $("#auth-downloadable-new").val();
            var dele = $("#auth-deletable-new").val();
            var upda = $("#auth-updatable-new").val();
            var visi = $("#auth-visible-new").val();
            var auths = toOneOrZero(down) + ",1," + toOneOrZero(dele) + "," + toOneOrZero(upda) + "," + toOneOrZero(visi);
            $.post("/auth", {files: files, users: users, auths: auths}, function (data) {
                layer.closeAll();
                getTabInfo("#auth-manager");
                $("#authAddedModal").modal("hide");
                alerts("添加" + boolToChinese(data.indexOf("success") > 0));
            })
        }
    });
    $("#user-auth-update-button").click(function () {
        var key = $(selectedRows[rowIndex]).children(".user-index").attr("data-key");
        var permission = $("#userAuthModal").find(".user-permission").val();
        layer.load(1);
        $.ajax({
            url: "/user/" + app.users[key].id + "/" + permission + "/",
            type: "PUT",
            success: function (data) {
                layer.closeAll();
                alerts(JSON.parse(data).message);
                if (data.indexOf("成功")) {
                    app.users[key].permission = permission;
                }
            }
        });
    });
    $("#user-password-update-button").click(function () {
        var password = $("#userPasswordModal").find(".user-password").val();
        if (isEmpty(password)) {
            alerts("密码不能为空");
        } else {
            var user = app.users[$(selectedRows[rowIndex]).children(".user-index").attr("data-key")];
            layer.load(1);
            $.ajax({
                url: "/user/reset/" + user.id + "/" + password + "/",
                type: "PUT",
                success: function (data) {
                    layer.closeAll();
                    alerts("更新" + boolToChinese(data.indexOf("success") > 0));
                }
            });
        }
    });
    $(".auth-delete").click(function () {
        doDelete("#auth-manager-table", app.auths, ".auth-index", "/auth/batch/");
    });
    $("#auth-update-button").click(function () {
        var down = $("#auth-downloadable").val();
        var dele = $("#auth-deletable").val();
        var upda = $("#auth-updatable").val();
        var visi = $("#auth-visible").val();
        var key = $(selectedRows[rowIndex]).children(".auth-index").attr("data-key");
        var id = app.auths[key].id;
        updateAuth("/auth/" + id, down, 1, dele, upda, visi, key);
    });
    $("#user-file-auth-update-button").click(function () {
        var key = $(selectedRows[rowIndex]).children(".user-index").attr("data-key");
        var parent = $("#userFileAuthModal");
        var down = $(parent).find(".user-downloadable").val();
        var uplo = $(parent).find(".user-uploadable").val();
        var dele = $(parent).find(".user-deletable").val();
        var upda = $(parent).find(".user-updatable").val();
        updateAuth("/user/" + app.users[key].id + "/auth", down, uplo, dele, upda, 1, key);
    });
    $("#file-auth-update-button").click(function () {
        var down = $("#file-downloadable-auth").val();
        var dele = $("#file-deletable-auth").val();
        var upda = $("#file-updatable-auth").val();
        var visi = $("#file-visible-auth").val();
        var id = app.files[$(selectedRows[rowIndex]).children(".file-index").attr("data-key")].id;
        //无须传递KEY
        updateAuth("/file/" + id + "/auth", down, 1, dele, upda, visi, 0);
    });
    setTimeout(function () {
        if (isEmpty(location.hash)) {
            location.hash = "#upload-manager";
            getTabInfo(location.hash);
        } else {
            $("a[href='" + location.hash + "']").click();
        }
    }, 1000);
    $("#server-file-share-button").click(function () {
        if (app.selectedServerFiles.length > 0) {
            var files = "";
            app.selectedServerFiles.forEach(function (json) {
                files += json.absolutePath + ",";
            });
            layer.load(1);
            $.post("/file/server/share", {
                prefix: $("#link-prefix").val(),
                files: files.substr(0, files.length - 1)
            }, function (data) {
                layer.closeAll();
                var json = JSON.parse(data);
                if (json.status === "success") {
                    getTabInfo(window.location.hash);
                    $("#fileAddedModal").modal("hide");
                    layer.msg("共享文件成功");
                } else {
                    alerts("共享失败（可能是本地路径或访问链接已经存在导致）");
                }
            });
        } else {
            alerts("还没有选择任何文件");
        }
    });
    $("#select-url").keyup(function () {
        if (window.event.keyCode === 13) {
            getServerFileByPath(false);
        }
    });
    $(".server-path-return").click(function () {
        var len = serverFileSearchHistory.length;
        if (len > 1) {
            serverFileSearchHistory.splice(len - 1, 1);
            $("#select-url").val(serverFileSearchHistory[len - 2]);
            serverFileSearchHistory.splice(len - 2, 1);
            getServerFileByPath(false);
        }
    });
    $(".to-upload-button").click(function () {
        layer.prompt({title: '请输入文件前缀，支持规则', formType: 0}, function (prefix, index) {
            layer.close(index);
            window.open("upload?prefix=" + encodeURI(prefix));
        });
    });
    $("#file-modify-button").click(function () {
        var localUrl = $("#new-file-local-url").val();
        var visitUrl = $("#new-file-visit-url").val();
        if (isEmpty(localUrl) && isEmpty(visitUrl)) {
            alerts("内容为空，无法更新");
        } else {
            layer.load(1);
            $.ajax({
                url: "/file/" + $("#file-id").val() + "/url",
                data: {oldLocalUrl: $("#old-file-local-url").val(), localUrl: localUrl, visitUrl: visitUrl},
                type: "PUT",
                success: function (data) {
                    layer.closeAll();
                    var json = JSON.parse(data);
                    var key = $(selectedRows[rowIndex]).children(".file-index").attr("data-key");
                    if (json.status.localUrl) {
                        app.files[key].localUrl = localUrl;
                        $("[data-toggle='tooltip']").tooltip();
                    }
                    if (json.status.visitUrl) {
                        app.files[key].visitUrl = visitUrl;
                    }
                    alerts((isEmpty(localUrl) ? "" : "更新本地路径" + boolToChinese(json.status.localUrl)) + (isEmpty(visitUrl) ? "" : (isEmpty(localUrl) ? "" : "，") + "更新访问链接" + boolToChinese(json.status.visitUrl)));
                },
                error: function () {
                    alerts("连接到服务器异常");
                }
            });
        }
    });
    $(".file-delete").click(function () {
        doDelete("#file-manager-table", app.files, ".file-index", "/file/batch/");
    });
});