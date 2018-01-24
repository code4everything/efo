var footer = "<div class='col-sm-12 text-center'><p>© 2018 zhazhapan Copyright.<br/>Powered by efo.</p></div>";

var globalConfig = {};

$(document).ready(function () {
    layer.load(1);
    $.get("/config/global", function (data) {
        layer.closeAll();
        globalConfig = JSON.parse(data);
        /** @namespace globalConfig.loadParticle */
        if (globalConfig.loadParticle) {
            // 加载 particle粒子效果
            particlesJS.load('particles-js', 'js/particles.json', function () {
                console.log('callback - particles.js config loaded');
            });
        }
        /** @namespace globalConfig.background.useImage */
        if (globalConfig.background.useImage) {
            var idx = 0;
            /** @namespace globalConfig.background.listGenerate */
            if (globalConfig.background.listGenerate.enable) {
                var start = globalConfig.background.listGenerate.start;
                var end = globalConfig.background.listGenerate.end;
                var len = end - start + 1;
                var list = new Array(len);
                for (var i = 0; i < len; i++) {
                    /** @namespace globalConfig.background.listGenerate.suffix */
                    list[i] = globalConfig.background.listGenerate.prefix + (start++) + globalConfig.background.listGenerate.suffix;
                }
                globalConfig.background.imageList = list;
            }
            if (globalConfig.background.random) {
                idx = Math.floor(Math.random() * globalConfig.background.imageList.length);
            } else {
                /** @namespace globalConfig.background.imageIndex */
                idx = globalConfig.background.imageIndex;
            }
            /** @namespace globalConfig.background.imageList */
            var url = globalConfig.background.imageList[idx];
            if (typeof url !== "undefined") {
                var body = $("body");
                $(body).css("background", "url('" + url + "') no-repeat center center");
                $(body).css("background-size", "cover");
            }
        }
    });
    // 加载页脚
    $("#footer").html(footer);
});