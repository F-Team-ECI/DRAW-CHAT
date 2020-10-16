var addChatModal;

$(document).ready(function () {

    addChatModal = $("#addChat:hidden");
    var span = document.getElementById("closeAddChat");

    span.onclick = function () {
        chatCreator.closeModal();
    }

    $(document).on("click", ".clickable-row", function() {
        var telefono = $(this).children().eq(0).text();
        var nombre = $(this).children().eq(0).text();
        var apellido = $(this).children().eq(0).text();
        var chat = {
            "usuario1": {
                "telefono": drawapp.getPhone(),
            },
            "usuario2": {
                "telefono": telefono,
            }
            
        }
        sendPostCreation(chat).then(confirmCreation);
    });

});

var sendPostCreation= function(data){
    var request = $.ajax({
        url: '/chats',
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json;charset=UTF-8',
        success: function (data, status, xhr) {
            console.log(status);
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log('Error' + errorMessage);
        }
    });
    return request;
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

var confirmCreation= function(data){
    chats.sendChatCreation(data);
}

chatCreator = (function(){

    return {

        openModal: function(){
            addChatModal.css({ display: "block" });
            $("#addChatLoad").css({ display: "block" })
            drawapp.setContacts(setContacts);
        },

        closeModal: function(){
            addChatModal.css({ display: "none" });
            $("#chatTableMain").empty();
        }
    }

})();