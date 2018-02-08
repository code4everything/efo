var app = new Vue({el: "#container", data: {categories: []}});

function getTabInfo(tabId) {
    if (tabId.indexOf("#") === 0) {
        tabId = tabId.substr(1);
    }
    if (tabId === "upload-manager") {

    } else if (tabId === "download-manager") {

    } else if (tabId === "file-manager") {

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
        }
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

function setCategoryToDefault() {
    $("#category-id").val(0);
    $("#category-key").val(0);
    $("#category-name").val("");
    $("#category-title").text("添加新分类");
}