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
            url: '/chats/users/'+drawapp.getPhone(),
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
        if(chat.user1.telefono === drawapp.getPhone()){
            return chat.user2;
        } else {
            return chat.user1;
        }
    }

    


    return {

        init: function () {
            chatsRequest().then(
            chatCreator.init);
        },
        addChat : function(chat){
            addChatToView(chat);
        }

    }

})();