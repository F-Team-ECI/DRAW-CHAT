chatSub = (function () {

    var chats = [];

    var addChatToView = function(chat){
        if(!chatVisible(chat)){
            drawchat(chat);
            chats.push(chat);
        }
    }

    var chatsRequest = function(){
        var req = $.ajax({
            url: '/chats/'+drawapp.getPhone(),
            type: "GET",
            success: function (data, status, xhr) {
                console.log('status: ' + status +"code" + xhr + ', data: ' + data);
                console.log(xhr.status);
                console.log(data);
                data.map(function(obj){
                    addChatToView(obj);
                });
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
            }
        });
        return req;
    }

    var drawchat = function(chat){
        var user  = getUserOfChat(chat);
        var element = "<div class='chatInstance'>"
        + "<i>" + user.nombre +"</i>"
        + "<i> </i>"
        + "<i>" + user.apellido + "</i>"
        + "<span>" + user.telefono + "</span>"
        +"<i style='display: none;'>" + chat.id + "</i>"
        + "</div>"
        $("#chatsContainer").append(element).hide().show(200);
    }

    var chatVisible = function(chat){
        var usuarioNuevo = getUserOfChat(chat).telefono;
        console.log(usuarioNuevo);
        var usuario = null;
        var c = null;
        for (i = 0; i < chats.length; i++) {
            c = chats[i];
            usuario = getUserOfChat(c).telefono;
            if(usuarioNuevo === usuario){
                return true;
            }
          }
        return false;
    }

    var getUserOfChat = function(chat){
        console.log(chat)
        if(chat.user1.telefono === drawapp.getPhone()){
            return chat.user2;
        } else {
            return chat.user1;
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
            chatsRequest();
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

$(document).ready(function () {
    $(document).on("click", ".chatInstance", function() {
        var name = $(this).children().eq(0).text();
        var apellido = $(this).children().eq(2).text();
        var telefono = $(this).children().eq(3).text();
        var id = $(this).children().eq(4).text();
        console.log(id);
    });
});