

chatSub = (function () {

    var connectAndSubscribe = function (cinema, date, movie) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/chats.' + drawapp.getPhone(), function (eventbody) {
                var theObject = JSON.parse(eventbody.body);
                console.log(theObject);
            });
        });
    };


    return {

        init: function () {
            connectAndSubscribe();
        },

        sendChatCreation: function (chat) {
            stompClient.send('/topic/chats.' + chat.usuario1.telefono, {}, JSON.stringify(chat));
            stompClient.send('/topic/chats.' + chat.usuario2.telefono, {}, JSON.stringify(chat));
        }

    }

})();

$(function () {
    $("#mainTextArea").keypress(function (e) {
        if (e.which == 13 && !e.shiftKey) {
            console.log($("#mainTextArea").val());
            e.preventDefault();
            
        }
    });
})