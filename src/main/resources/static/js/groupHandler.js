var addMemberModal;
var deleteMemberModal;
var groupHanlder = (function(){


    var currentId = null;

    $(document).ready(function () {

        addMemberModal = $("#addMemberModal:hidden");
        var span = document.getElementById("closeAddMember");

        span.onclick = function () {
            groupHanlder.closeAddModal();
        }

        deleteMemberModal = $("#deleteMemberModal:hidden");
        var spanDelete = document.getElementById("closeDeleteMember");

        spanDelete.onclick = function () {
            groupHanlder.closeDeleteModal();
        }
    });

    var loadRemainingUers = function(callback){
        console.log(conversationChat.getCurrentID )
        var req = $.ajax({
            url: '/groups/' + currentId +'/remaining',
            type: "GET",
            success: function (data, status, xhr) {
                console.log('status: ' + status +"code" + xhr + ', data: ' + data);
                console.log(xhr.status);
                console.log(data);
                callback(data, status, xhr)
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
            }
        });
        return req;
    }


    var addRemainingUsersToView = function(data, status, xhr){
        $("#addMemberLoad").css({ display: "none" })
        $("#addMemberMain").append("<tbody>"
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
                $("#addMemberMain tbody").append("<tr class='groupAdd'>"
                    + "<td>" + con.telefono + "</td>"
                    + "<td>" + con.nombre + "</td>"
                    + "<td>" + con.apellido + "</td>"
                    + "<td>" + con.estado + "</td>"
                    + "<td> <input type='checkbox'></input> </td>"
                    + "</tr>")
            }
        })
    }

    var loadGroupUsers = function(callback){
        var req = $.ajax({
            url: '/groups/' + currentId + "/members",
            type: "GET",
            success: function (data, status, xhr) {
                console.log('status: ' + status +"code" + xhr + ', data: ' + data);

                callback(data, status, xhr)
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
            }
        });
        return req;
    }

    var addMembersToView = function(data, status, xhr){
        $("#deleteMemberLoad").css({ display: "none" })
        $("#deleteMemberMain").append("<tbody>"
            + "<tr>"
            + "<th>TELÉFONO</th>"
            + "<th>NOMBRE</th>"
            + "<th>APELLIDO</th>"
            + "<th>ESTADO</th>"
            + "<th>ELIMINAR</th>"
            + "</tr>"
            + "</tbody>"
        );
        data.map(function (con) {
            if (con.telefono != drawapp.getPhone()) {
                $("#deleteMemberMain tbody").append("<tr class='groupDel'>"
                    + "<td>" + con.telefono + "</td>"
                    + "<td>" + con.nombre + "</td>"
                    + "<td>" + con.apellido + "</td>"
                    + "<td>" + con.estado + "</td>"
                    + "<td> <input type='checkbox'></input> </td>"
                    + "</tr>")
            }
        })

    }

    var sendPutAddMembers = function(members){
        var object = {
            "id": currentId,
            "members": members
        }
        $.ajax({
            url: '/groups/addmembers',
            type: "PUT",
            data: JSON.stringify(object),
            contentType: 'application/json;charset=UTF-8',
            success: function (data, status, xhr) {
                console.log('status: ' + status + ', data: ' + data);
                $("#addMemberIncorrect").css({ display: "none" })
                $("#addMemberCorrect").css({ display: "block" })
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
                $("#addMemberIncorrect").css({ display: "block" })
                $("#addMemberCorrect").css({ display: "none" })
            }
        });
    }

    var sendPutDeleteMembers = function(members){
        var object = {
            "id": currentId,
            "members": members
        }
        $.ajax({
            url: '/groups/deletemembers',
            type: "PUT",
            data: JSON.stringify(object),
            contentType: 'application/json;charset=UTF-8',
            success: function (data, status, xhr) {
                console.log('status: ' + status + ', data: ' + data);
                $("#deleteMemberIncorrect").css({ display: "none" })
                $("#deleteMemberCorrect").css({ display: "block" })
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
                $("#deleteMemberIncorrect").css({ display: "block" })
                $("#deleteMemberCorrect").css({ display: "none" })
            }
        });
    }

    
    return {

        closeAddModal: function(){
            addMemberModal.css({ display: "none" });
            $("#addMemberIncorrect").css({ display: "none" })
            $("#addMemberCorrect").css({ display: "none" })
            $("#addMemberMain").empty();
        },

        openAddModal: function(){
            addMemberModal.css({ display: "flex" });  
            $("#addMemberLoad").css({ display: "block" })
            currentId = conversationChat.getCurrentID() 
            loadRemainingUers(addRemainingUsersToView)

        }, 
        
        addMembers: function(){
            var members = [];
            var elemen = 0;
            var table = $('#addMemberMain tr').each(function () {
                var row = $(this);
                var tel = row.find('td').first().text();
                if (elemen != 0) {
                    if (row.find('td').last().find('input').is(':checked')) {
                        members.push({ "telefono": Number(tel) })
                    }
                }
                elemen += 1;
            });
            sendPutAddMembers(members)
        },

        closeDeleteModal: function(){
            deleteMemberModal.css({ display: "none" });
            $("#deleteMemberIncorrect").css({ display: "none" })
            $("#deleteMemberCorrect").css({ display: "none" })
            $("#deleteMemberMain").empty();
        },

        openDeleteModal: function(){
            deleteMemberModal.css({ display: "flex" });
            $("#deleteMemberLoad").css({ display: "block" });
            currentId = conversationChat.getCurrentID();
            loadGroupUsers(addMembersToView);
        },

        deleteMembers: function(){
            var members = [];
            var elemen = 0;
            var table = $('#deleteMemberMain tr').each(function () {
                var row = $(this);
                var tel = row.find('td').first().text();
                if (elemen != 0) {
                    if (row.find('td').last().find('input').is(':checked')) {
                        members.push({ "telefono": Number(tel) })
                    }
                }
                elemen += 1;
            });
            sendPutDeleteMembers(members)
            
        }



    }

})();