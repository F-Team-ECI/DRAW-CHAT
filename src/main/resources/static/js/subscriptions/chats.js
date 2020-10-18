chatSub = (function () {

    var chats = [];

    var addChatToView = function(chat){
        if(!chatVisible(chat)){
            drawchat(chat);
            console.log(chats);
            chats.push(chat);
            console.log(chats);
        }
    }

    var drawchat = function(chat){
        var element = "<div class='chatInstance'>"
        + getUserOfChat(chat)
        + "</div>"
        $("#chatsContainer").append(element);
    }

    var chatVisible = function(chat){
        var usuarioNuevo = getUserOfChat(chat);
        console.log(usuarioNuevo);
        var usuario = null;
        var c = null;
        for (i = 0; i < chats.length; i++) {
            c = chats[i];
            console.log(c);
            usuario = getUserOfChat(c);
            console.log(usuario);
            if(usuarioNuevo === usuario){
                return true;
            }
          }
        return false;
    }

    var getUserOfChat = function(chat){
        console.log(chat)
        if(chat.user1.telefono === drawapp.getPhone()){
            return chat.user2.telefono;
        } else {
            return chat.user1.telefono;
        }
    }

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
                addChatToView(theObject);
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