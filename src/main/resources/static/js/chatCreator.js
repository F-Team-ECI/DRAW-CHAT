var addChatModal;
chatCreator = (function () {

    var stompClient = null;

    $(document).ready(function () {

        addChatModal = $("#addChat:hidden");
        var span = document.getElementById("closeAddChat");

        span.onclick = function () {
            chatCreator.closeModal();
        }

        $(document).on("click", ".clickable-row", function () {
            var telefono = $(this).children().eq(0).text();
            var nombre = $(this).children().eq(1).text();
            var apellido = $(this).children().eq(2).text();
            var chat = {
                "user1": {
                    "telefono": drawapp.getPhone(),
                    "nombre": drawapp.getUser().nombre,
                    "apellido": drawapp.getUser().apellido
                },
                "user2": {
                    "telefono": telefono,
                    "nombre": nombre,
                    "apellido": apellido
                }

            }
            sendPostCreation(chat);
        });

    });

    var sendPostCreation = function (data) {
        stompClient.send('/app/chatsCreator', {}, JSON.stringify(data));
    }

    var setContacts = function (data, status, xhr) {
        console.log(data);
        $("#addChatLoad").css({ display: "none" })
        $("#chatTableMain").append("<tbody>"
            + "<tr>"
            + "<th>TELÉFONO</th>"
            + "<th>NOMBRE</th>"
            + "<th>APELLIDO</th>"
            + "<th>ESTADO</th>"
            + "</tr>"
            + "</tbody>"
        );
        data.map(function (con) {
            $("#chatTableMain tbody").append("<tr class='clickable-row'>"
                + "<td>" + con.telefono + "</td>"
                + "<td>" + con.nombre + "</td>"
                + "<td>" + con.apellido + "</td>"
                + "<td>" + con.estado + "</td>"
                + "</tr>")
        })

        var cont = $("#addChatTable");
        console.log(cont);
        cont.css({ display: "block" });
    }

    var confirmCreation = function (data) {
        chats.sendChatCreation(data);
    }



    var connectAndSubscribe = function (cinema, date, movie) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect(config.getToken(), function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/chatsCreator.' + drawapp.getPhone(), function (eventbody) {
                var theObject = JSON.parse(eventbody.body);
                console.log(theObject);
                if(chatSub != undefined){
                    chatSub.addChat(theObject);
                }
            });
        });
    };

    

    return {
        init: function(){
            connectAndSubscribe();
        },
        openModal: function () {
            addChatModal.css({ display: "block" });
            $("#addChatLoad").css({ display: "block" })
            drawapp.setContacts(setContacts);
        },

        closeModal: function () {
            addChatModal.css({ display: "none" });
            $("#chatTableMain").empty();
        }
    }

})();