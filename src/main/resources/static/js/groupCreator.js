var addGroupModal;
var groupCreator = (function () {

    var stompClient = null;

    $(document).ready(function () {

        addGroupModal = $("#addGroup:hidden");
        var span = document.getElementById("closeAddGroup");

        span.onclick = function () {
            groupCreator.closeModal();
        }
    });

    var setContacts = function (data, status, xhr) {

        $("#addGroupLoad").css({ display: "none" })
        $("#groupTableMain").append("<tbody>"
            + "<tr>"
            + "<th>TELÉFONO</th>"
            + "<th>NOMBRE</th>"
            + "<th>APELLIDO</th>"
            + "<th>ESTADO</th>"
            + "<th>AGREGAR</th>"
            + "</tr>"
            + "</tbody>"
        );
        data.map(function (con) {
            if (con.telefono != drawapp.getPhone()) {
                $("#groupTableMain tbody").append("<tr class='groupLine'>"
                    + "<td>" + con.telefono + "</td>"
                    + "<td>" + con.nombre + "</td>"
                    + "<td>" + con.apellido + "</td>"
                    + "<td>" + con.estado + "</td>"
                    + "<td> <input type='checkbox'></input> </td>"
                    + "</tr>")
            }
        })

    }

    var sendPostCreation = function (name, lema, members) {
        var object = {
            "nombre": name,
            "lema": lema,
            "members": members
        }
        console.log(object)
        $.ajax({
            url: '/groups/' + drawapp.getPhone(),
            type: "POST",
            data: JSON.stringify(object),
            contentType: 'application/json;charset=UTF-8',
            success: function (data, status, xhr) {
                console.log('status: ' + status + ', data: ' + data);
                $("#groupIncorrect").css({ display: "none" });
                $("#groupCorrect").css({ display: "block" });
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
                $("#groupIncorrect").css({ display: "block" });
                $("#groupCorrect").css({ display: "none" });
            }
        });
    }

    var connectAndSubscribe = function (cinema, date, movie) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        connected = true;
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect(config.getToken(), function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/groupsCreator.' + drawapp.getPhone(), function (eventbody) {
                var theObject = JSON.parse(eventbody.body);
                console.log(theObject);
                if(chatSub != undefined){
                    groupSub.addGroup(theObject);
                }
            });
        });
    };



    return {

        init: function () {
            console.log("REDONE")
            connectAndSubscribe();
        },

        openModal: function () {
            addGroupModal.css({ display: "block" });
            $("#addGroupLoad").css({ display: "block" })
            drawapp.setContacts(setContacts);
        },

        closeModal: function () {
            addGroupModal.css({ display: "none" });
            $("#groupTableMain").empty();
            $("#groupIncorrect").css({ display: "none" });
            $("#groupCorrect").css({ display: "none" });
        },

        create: function () {
            var $set = $('.groupLine');
            var members = [];
            var elemen = 0;
            var table = $('#groupTableMain tr').each(function () {
                var row = $(this);
                var tel = row.find('td').first().text();
                if (elemen != 0) {
                    if (row.find('td').last().find('input').is(':checked')) {
                        members.push({ "telefono": Number(tel) })
                    }
                }
                elemen += 1;
            });
            var name = $("#groupName").val();
            var lema = $("#lema").val();
            sendPostCreation(name, lema, members);
        }
    }


})();