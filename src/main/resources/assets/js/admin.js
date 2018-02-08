function getTabInfo(tabId) {
    if (tabId === "upload-manager") {

    } else if (tabId === "download-manager") {

    } else if (tabId === "file-manager") {

    } else if (tabId === "auth-manager") {

    } else if (tabId === "category-manager") {

    } else if (tabId === "user-manager") {

    } else if (tabId === "admin-manager") {

    } else if (tabId === "system-setting") {

    } else {
        alerts("没有找到可执行的方法");
    }
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
                    responseTip(data);
                }
            });
        } else {
            $.post("/category/" + name, function (data) {
                responseTip(data);
            });
        }
    }
}