var conversationChat = (function () {

    var selectedchat = null;
    var currentId = null;
    var stompClient = null;
    var connected = false;
    var loading = false;
    var textContent = null;

    var playSound = function(au) {
        var url = window.location.href+ au;
        const audio = new Audio(url);
        audio.play();
    }

    var connectAndSubscribe = function (chatId) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/chats/messages.' + chatId, function (eventbody) {
                var theObject = JSON.parse(eventbody.body);
                console.log(theObject);
                $("#chatBox").append(drawMessage(theObject));
                var objDiv = document.getElementById("chatBox");
                objDiv.scrollTop = objDiv.scrollHeight;
                $("#mainTextArea").val("");
                playSound("tone.mp3");
            });
        });

    };

    var sortMessages = function (messages) {
        function compare(a, b) {
            // Use toUpperCase() to ignore character casing
            const bandA = a.fecha.toUpperCase();
            const bandB = b.fecha.toUpperCase();
            let comparison = 0;
            if (bandA > bandB) {
                comparison = 1;
            } else if (bandA < bandB) {
                comparison = -1;
            }
            return comparison;
        }
        return messages.sort(compare);

    }

    var drawMessage = function(m){
        var ans = "";
        if(m.emisor.telefono === drawapp.getPhone()){
            ans += "<div class='rightContainer'><div class='rightMessage'>"
                + m.contenido+"<i style='display:none'>" + m.id +"</i>"
                + "</div></div>";
        } else {
            ans += "<div class='leftContainer'><div class='leftMessage'>"
                + m.contenido+"<i style='display:none'>" + m.id +"</i>"
                + "</div></div>";
        }
        ans+="<hr></hr>";
        return ans;
    }

    var drawChat = function (messages) {
        sorted = sortMessages(messages);
        console.log(sorted);
        $("#chatBox").empty();
        textContent = "";
        sorted.map(function(data){
            textContent += drawMessage(data);
        });
        $("#chatBox").append(textContent).hide().show(200);
        var objDiv = document.getElementById("chatBox");
        objDiv.scrollTop = objDiv.scrollHeight;
    }

    var conversationRequest = function (id) {
        var req = $.ajax({
            url: '/chats/' + id + '/messages',
            type: "GET",
            success: function (data, status, xhr) {
                console.log('status: ' + status + "code" + xhr + ', data: ' + data);
                console.log(xhr.status);
                console.log(data);
                loading = false;
                drawChat(data);
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
            }
        });
    }

    var getConversation = function () {
        if (loading === false) {
            $("#chatBox").append(
                "<div class='loadingchat'>"
                + "CARGANDO..."
                + "</div>"
            );
            loading = true;
        }
        conversationRequest(currentId);

    }

    return {

        setSelectedchat: function (cell) {
            if (selectedchat != null) {
                $(selectedchat).css("background-color", "white");
            }
            selectedchat = cell;
            currentId = $(cell).children().eq(4).text();
            $(cell).css("background-color", "#c2bbbb");
            getConversation();
        },

        subscribe: function () {
            connectAndSubscribe(currentId);
        },

        disconnect: function () {
            if (stompClient !== null && connected === true) {
                stompClient.disconnect();
                connected = false;
            }
            console.log("Disconnected");
        },

        sendMessage: function (content) {
            mess = {
                "chat": {
                    "id": currentId
                },
                "contenido": content
            }
            var req = $.ajax({
                url: '/chats/' + currentId + '/messages',
                type: "POST",
                data: JSON.stringify(mess),
                contentType: 'application/json;charset=UTF-8',
                success: function (data, status, xhr) {
                    console.log('status: ' + status + "code" + xhr + ', data: ' + data);
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
    $(document).on("click", ".chatInstance", function () {
        conversationChat.setSelectedchat(this);
        conversationChat.disconnect();
        conversationChat.subscribe();
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


