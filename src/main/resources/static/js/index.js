var footer = "<p> © 2018 zhazhapan Copyright.&emsp;&emsp;Powered by efo.</p>";

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
            if (globalConfig.background.random) {
                idx = Math.floor(Math.random() * globalConfig.background.imageList.length);
            } else {
                /** @namespace globalConfig.background.imageIndex */
                idx = globalConfig.background.imageIndex;
            }
            /** @namespace globalConfig.background.imageList */
            var url = globalConfig.background.imageList[idx];
            if (typeof url !== "undefined") {
                $("body").css("background", "url('" + url + "') no-repeat center center");
            }
        }
    });
    // 加载页脚
    $("#footer").html(footer);
});