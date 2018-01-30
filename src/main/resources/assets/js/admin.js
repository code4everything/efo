function saveCategory() {
    var name = $("#category-name").val();
    if (isEmpty(name)) {
        alerts("分类名不能为空");
    } else {
        var id = $("#category-id").val();
        layer.load(1);
        if (id > 0) {
            $.ajax({
                url: "/category/update", type: "PUT", data: {id: id, name: name}, success: function (data) {
                    responseTip(data);
                }
            });
        } else {
            $.post("/category/add", {name: name}, function (data) {
                responseTip(data);
            });
        }
    }
}