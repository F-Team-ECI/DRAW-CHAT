var conversationChat = (function () {

    var selectedchat = null;
    var currentId = null;
    var stompClient = null;
    var connected = false;
    var loading = false;
    var textContent = null;

    var playSound = function (au) {
        var url = window.location.href + au;
        const audio = new Audio(url);
        audio.play();
    }

    var COLOR = ["#32CD32", "#48D1CC", "#F4A460", "#778899", "#808000"];
    var index = 0;

    var MODES = {
        "CHAT": "CHAT",
        "GROUP": "GROUP"
    }

    var CURRENT_MODE;

    var connectAndSubscribe = function (chatId) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect(config.getToken(), function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/chatsMessages.' + chatId, function (eventbody) {
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

    var connectAndSubscribeGroup = function (chatId) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect(config.getToken(), function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/groupsMessages.' + chatId, function (eventbody) {
                var theObject = JSON.parse(eventbody.body);
                console.log(theObject);
                $("#chatBox").append(drawMessage(theObject));
                var objDiv = document.getElementById("chatBox");
                objDiv.scrollTop = objDiv.scrollHeight;
                $("#mainTextArea").val("");
                playSound("tone.mp3");
            });
        });
    }

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

    var drawMessage = function (m) {
        var ans = "";
        var added = CURRENT_MODE === MODES.GROUP ? "<span style='color:" + COLOR[index] + "'>" + m.emisor.nombre + " " + m.emisor.apellido + "</span>" : "";
        index = (index+1)%COLOR.length
        if (m.emisor.telefono === drawapp.getPhone()) {
            ans += "<div class='rightContainer'><div class='rightMessage'>"
                + m.contenido + "<i style='display:none'>" + m.id + "</i>"
                + "</div></div>";
        } else {
            ans += "<div class='leftContainer'>"
                + "<div class='leftMessage'>"
                + added
                + "<div>"+ m.contenido + "</div>" + "<i style='display:none'>" + m.id + "</i>"
                + "</div></div>";
        }
        ans += "<hr></hr>";
        return ans;
    }

    var drawChat = function (messages) {
        sorted = sortMessages(messages);
        console.log(sorted);
        $("#chatBox").empty();
        textContent = "";
        sorted.map(function (data) {
            textContent += drawMessage(data);
        });
        $("#chatBox").append(textContent).hide().show(200);
        var objDiv = document.getElementById("chatBox");
        objDiv.scrollTop = objDiv.scrollHeight;
    }

    var conversationRequest = function (id) {
        var url1 = CURRENT_MODE === MODES.CHAT ? '/chats/' + id + '/messages' : '/groups/' + id + '/messages'
        var req = $.ajax({
            url: url1,
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
            if (CURRENT_MODE === MODES.GROUP) {
                connectAndSubscribeGroup(currentId);
            } else {
                connectAndSubscribe(currentId);
            }

        },

        disconnect: function () {
            if (stompClient !== null && connected === true) {
                stompClient.disconnect();
                connected = false;
            }
            console.log("Disconnected");
        },

        changeToChat: function () {
            CURRENT_MODE = MODES.CHAT;
            $("#groupButtonsDiv").css("display", "none")
            $("#chatBox").css("height", "90%")
        },

        changeToGroup: function () {
            CURRENT_MODE = MODES.GROUP;
            $("#groupButtonsDiv").css("display", "flex")
            $("#chatBox").css("height", "85%")
        },

        sendMessage: function (content) {
            if (CURRENT_MODE === MODES.GROUP) {
                mess = {
                    "grupo": {
                        "id": currentId
                    },
                    "contenido": content
                }
                stompClient.send('/app/groupsMessages.' + currentId, {}, JSON.stringify(mess));
            } else {
                mess = {
                    "chat": {
                        "id": currentId
                    },
                    "contenido": content
                }
                stompClient.send('/app/chatsMessages.' + currentId, {}, JSON.stringify(mess));
            }
        },

        getCurrentID: function(){
            return currentId;
        }

    }
})();

$(document).ready(function () {
    $(document).on("click", ".chatInstance", function () {
        conversationChat.setSelectedchat(this);
        conversationChat.changeToChat();
        conversationChat.disconnect();
        conversationChat.subscribe();
    });
});

$(document).ready(function () {
    $(document).on("click", ".groupInstance", function () {
        conversationChat.setSelectedchat(this);
        conversationChat.changeToGroup();
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


