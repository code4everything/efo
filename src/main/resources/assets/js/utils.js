function getQuery(key) {
    var queryString = location.search;
    if (queryString.indexOf("?") === 0) {
        queryString = queryString.substr(1);
        var query = queryString.split("&");
        for (var i = 0; i < query.length; i++) {
            if (query[i].indexOf(key + "=") > -1) {
                return query[i].split("=")[1];
            }
        }
    }
    return "";
}

function getCookie(name) {
    if (document.cookie.length > 0) {
        var start = document.cookie.indexOf(name + "=");
        if (start !== -1) {
            start += name.length + 1;
            var end = document.cookie.indexOf(";", start);
            if (end === -1) {
                end = document.cookie.length;
            }
            return document.cookie.substring(start, end);
        }
    }
    return "";
}

function isEmpty(string) {
    return typeof string === "undefined" || string.length < 1;
}

function isEmail(email) {
    return email.match(/^[0-9a-z\-]+([0-9a-z\-]|(\.[0-9a-z\-]+))*@[0-9a-z\-]+(\.[0-9a-z\-]+)+$/);
}

function alerts(msg, callback) {
    layer.alert(msg, {
        skin: 'layui-layer-molv'
        , closeBtn: 0
    }, callback);
}

Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};