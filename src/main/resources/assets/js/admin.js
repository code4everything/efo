var app = new Vue({el: "#container", data: {categories: [], downloaded: [], uploaded: [], files: [], serverFiles: []}});

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

    } else if (tabId === "category-manager") {
        setCategoryToDefault();
        getCategory();
    } else if (tabId === "user-manager") {

    } else if (tabId === "admin-manager") {

    } else if (tabId === "system-setting") {

    } else {
        alerts("没有找到可执行的方法");
    }
    setTimeout(function () {
        $('[data-toggle="tooltip"]').tooltip();
        setCSS();
    }, 1000);
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
    return {
        user: enableSearch ? $(fileFilterDivParent).find(".user-filter").val() : "",
        file: enableSearch ? $(fileFilterDivParent).find(".file-name-filter").val() : "",
        category: enableSearch ? $(fileFilterDivParent).find(".category-filter").val() : "",
        offset: offset
    }
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
    layer.load(1);
    $.get("/file/server", {path: $("#select-url").val()}, function (data) {
        layer.closeAll();
        app.serverFiles = JSON.parse(data);
        var ele = $("#server-file-list-group");
        $(ele).empty();
        $.each(app.serverFiles, function (i, json) {
            console.info(json);
            var li = "<li class='list-group-item list-group-item-info' data-key='" + i + "'><a href='javascript:'>" + json.name + "</a></li>";
            $(ele).append(li);
        });
        $("#fileAddedModal").modal("show");
    })
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

function shareFiles() {

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

$(document).ready(function () {
    setTimeout(function () {
        if (isEmpty(location.hash)) {
            location.hash = "#upload-manager";
            getTabInfo(location.hash);
        } else {
            $("a[href='" + location.hash + "']").click();
        }
    }, 1000);
    $(".server-file-share-button").click(function () {

    });
});
