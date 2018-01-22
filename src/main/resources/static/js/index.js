var footer = "<p> © 2018 zhazhapan.&emsp;&emsp;Powered by efo.</p>";

var globalConfig = {};

$(document).ready(function () {
    layer.load(1);
    $.get("/config/global", function (data) {
        layer.closeAll();
        globalConfig = JSON.parse(data);
        console.info(data);
        if (globalConfig.loadParticle == 1) {
            // 加载 particle粒子效果
            particlesJS.load('particles-js', '../static/js/particles.json', function () {
                console.log('callback - particles.js config loaded');
            });
        }
    });
    // add footer
    $("#footer").html(footer);
});