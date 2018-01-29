$("#file-input").fileinput({
    uploadUrl: "/file/upload",
    uploadAsync: true,
    maxFileCount: 100
}).on('fileuploaded', function (event, data, previewId, index) {
});