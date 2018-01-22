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
    });
    // 加载页脚
    $("#footer").html(footer);
});