var canvas = (function () {
    var canvas = null;
    var color = null;
    var currentGroup = null;

    var hexDigits = new Array
        ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f");

    var connected = false;
    var stompClient = null;
    var getMousePosition = function (evt) {
        $('#myCanvas').click(function (e) {
            var rect = canvas.getBoundingClientRect();
            var x = e.clientX - rect.left;
            var y = e.clientY - rect.top;
            console.info(x + " - " + y);
            point = {
                "x": x,
                "y": y,
                "color": color
            }
            console.log(point);
            sendPoint(point);
        });
    }

    var drawPoint = function(point){
        var ctx = canvas.getContext("2d");
        ctx.fillStyle = point.color;
        ctx.fillRect(point.x, point.y , 5 , 5);
    }

    var suscribe = function (group) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;
        currentGroup = group;

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/paint.'+ group, function (eventbody) {
                console.log(JSON.parse(eventbody.body));
                drawPoint(JSON.parse(eventbody.body));
            });
        });
    }

    var sendPoint = function(point){
        stompClient.send('/app/paint.'+currentGroup, {}, JSON.stringify(point));
    }

    //Function to convert rgb color to hex format
    function rgb2hex(rgb) {
        rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
        return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    }

    function hex(x) {
        return isNaN(x) ? "00" : hexDigits[(x - x % 16) / 16] + hexDigits[x % 16];
    }

    return {

        init: function () {
            canvas = document.getElementById("myCanvas");
            canvas.width = window.innerWidth * 0.998;
            canvas.height = window.innerHeight * 0.993 - $("#upperDiv").height();
            console.log($("#upperDiv").height());
            canvas.addEventListener('Mousedown', getMousePosition(), false);
            suscribe("test");
            color=rgb2hex($("#red").css("background-color"));
        },
        setColor: function (butt) {
            var el = "#" + butt.id;
            color = rgb2hex($(el).css("background-color"));
            console.log(color);
        },

        setCustomColor: function () {
            color = $("#custom").val();
            console.log(color);
        },
        disconnect: function () {
            if (stompClient !== null && connected === true) {
                stompClient.disconnect();
                connected = false;
            }
            console.log("Disconnected");
        },



    }



})();