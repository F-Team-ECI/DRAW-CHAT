contacts = (function(){

    var setContacts = function (data, status, xhr) {
        console.log(data);
        $("#contactsLoad").css({ display: "none" })
        $("#contactList").append("<table id='contactTable'>"
            + "<tr>"
            + "<th>TELÉFONO</th>"
            + "<th>NOMBRE</th>"
            + "<th>APELLIDO</th>"
            + "<th>ESTADO</th>"
            + "</tr>"
            + "<table>")
        data.map(function (con) {
            $("#contactTable").append("<tr>"
                + "<td>" + con.telefono + "</td>"
                + "<td>" + con.nombre + "</td>"
                + "<td>" + con.apellido + "</td>"
                + "<td>" + con.estado + "</td>"
                + "</tr>")
        })
        var cont = $("#contactList:hidden");
        cont.css({ display: "block" });
    }

    return {
        addContact: function(){
            modalContacts.css({ display: "block" });
        },

        closeAdd: function(){
            $("#contactIncorrect").css({ display: "none" });
            $("#contectSuccess").css({ display: "none" });
            modalContacts.css({ display: "none" });
        },

        seeContacts: function(){
            seeAll.css({ display: "block" });
            $("#contactsLoad").css({ display: "block" })
            $("#contactList").empty();
            drawapp.setContacts(setContacts);
        },

        closeSeeContacts(){
            seeAll.css({ display: "none" });
        }
    }
})();