$("#file-input").fileinput({
    uploadUrl: "/file",
    uploadAsync: true,
    maxFileCount: 100,
    previewFileType: ['image', 'html', 'text', 'video', 'audio', 'flash'],
    uploadExtraData: function () {
        return {
            categoryId: $("#category-id").val(),
            tag: $("#tag").val(),
            description: $("#description").val(),
            prefix: getQuery("prefix")
        };
    },
    maxFilePreviewSize: 51200
}).on('fileuploaded', function (event, data, previewId, index) {
    var json = data.response;
    if (json.status === "success") {
        alerts("上传成功");
    } else {
        alerts("上传失败，文件不合法");
    }
});

$(document).on('ready', function () {
    $("#file-input").fileinput({
        maxFilePreviewSize: 10240
    });
});

$.get("/category/all", function (data) {
    var json = JSON.parse(data);
    var option = "";
    $.each(json, function (i, category) {
        option += "<option value='" + category.id + "'>" + category.name + "</option>";
    });
    if (!isEmpty(option)) {
        $("#category-id").html(option);
    }
});