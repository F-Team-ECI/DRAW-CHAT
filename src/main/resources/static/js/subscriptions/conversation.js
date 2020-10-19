var conversationChat = (function(){

    var selectedchat = null;
    var currentId = null;
    var stompClient = null;
    var connected = false;


    var connectAndSubscribe = function (chatId) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            console.log("AYYYYYYYYYYYYYYYYYYY 1");
            stompClient.subscribe('/topic/chats/messages.'+chatId, function (eventbody) {
                var theObject = JSON.parse(eventbody.body);
                console.log(theObject);
            });
        });

    };

    return {

        setSelectedchat : function(cell){
            if(selectedchat != null){
                $(selectedchat).css("background-color", "white");
            }
            selectedchat = cell;
            currentId = $(cell).children().eq(4).text();
            $(cell).css("background-color", "#c2bbbb");
        },

        subscribe: function(){
            connectAndSubscribe(currentId);
        },

        disconnect: function () {
            if (stompClient !== null && connected === true) {
                stompClient.disconnect();
                connected = false;
            }
            console.log("Disconnected");
        },

        sendMessage: function(content){
            mess = {
                "chat": {
                    "id": currentId
                },
                "contenido": content
            }
            var req = $.ajax({
                url: '/chats/'+currentId + '/messages',
                type: "POST",
                data: JSON.stringify(mess),
                contentType: 'application/json;charset=UTF-8',
                success: function (data, status, xhr) {
                    console.log('status: ' + status +"code" + xhr + ', data: ' + data);
                    console.log(xhr.status);
                    console.log(data);
                },
                error: function (jqXhr, textStatus, errorMessage) {
                    console.log('Error' + errorMessage);
                }
            });
            return req;
        }

    }
})();

$(document).ready(function () {
    $(document).on("click", ".chatInstance", function() {
        conversationChat.setSelectedchat(this);
        conversationChat.disconnect();
        conversationChat.subscribe();
        var name = $(this).children().eq(0).text();
        var apellido = $(this).children().eq(2).text();
        var telefono = $(this).children().eq(3).text();
        var id = $(this).children().eq(4).text();
    });
});

$(function () {
    $("#mainTextArea").keypress(function (e) {
        if (e.which == 13 && !e.shiftKey) {
            var message = $("#mainTextArea").val();
            e.preventDefault();
            conversationChat.sendMessage(message);
        }
    });
})


