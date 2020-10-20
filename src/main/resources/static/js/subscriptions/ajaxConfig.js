
var config = (function () {
    var sesssiontoken = null;

    var setConfig = function () {

        var token = sesssiontoken.token;
        var header = sesssiontoken.headerName;

        $(document).ajaxSend(function (e, xhr, options) {
            console.log(header);
            console.log(token);
            xhr.setRequestHeader(header, token);
        });


    }


    var setToken = function () {
        var req = $.ajax({
            url: '/csrf',
            type: "GET",
            success: function (data, status, xhr) {
                console.log('status: ' + status + "code" + xhr + ', data: ' + data);
                console.log(data);
                sesssiontoken = data;
                setConfig();
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
            }
        });
        return req;
    }


    return {
        init: function () {
            return setToken();
        },

        getToken: function () {
            var headers = {};
            headers['X-CSRF-TOKEN'] = sesssiontoken.token;
            return headers;
        }
    }







})();
