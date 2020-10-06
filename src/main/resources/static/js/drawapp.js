var drawapp = (function () {
    var userR;
    var setUser = function (user) {
        console.log(user);
        userR = user;
        $("#user").text(user.nombre + " " + user.apellido);
    }

    var addContactSuccess = function (data, status, xhr) {
        console.log(data)
        console.log(status)
    }

    var updateUser = function () {
        register.me(setUser);
    }

    updateUser();

    var setContacts = function (data, status, xhr) {
        console.log(data);
        data.map(function (con) {
            $("#contactList").append("<div>" + con.telefono + "</div>")
        })
        var cont = $("#contactList:hidden");
        cont.css({ display: "block" });
    }


    var sendUpdateSettings = function (data) {
        console.log(data);
    }

    return {

        saveContact: function () {
            var a = $("#newPhone").val();
            var number = Number(a);
            console.log(number);
            $.ajax({
                url: '/contacts',
                type: "POST",
                data: "userA=" + userR.telefono + "&userB=" + number,
                contentType: 'application/x-www-form-urlencoded; charset=utf-8',
                success: function (data, status, xhr) {
                    setUser(data, status, xhr);
                },
                error: function (jqXhr, textStatus, errorMessage) {
                    console.log('Error' + errorMessage);
                }
            });
        },
        setContacts: function () {
            console.log(userR)
            $.ajax({
                url: '/users/' + userR.telefono + '/contacts',
                type: "GET",
                success: function (data, status, xhr) {
                    setContacts(data, status, xhr);
                },
                error: function (jqXhr, textStatus, errorMessage) {
                    console.log('Error' + errorMessage);
                }
            });
        },

        updateSettings: function () {
            var newName = $("#ajusteNombre").val();
            var newLastName = $("#ajusteApellido").val();
            var newPassword = $("#ajusteContraseña").val();
            var data = { "telefono": userR.telefono };
            if (newName.length !== 0) {
                data.nombre = newName;
            }
            if (newLastName.length !== 0) {
                data.apellido = newLastName;
            }
            if (newPassword.length !== 0) {
                data.contraseña = newPassword;
            }
            sendUpdateSettings(data).then(updateUser);

        }
    }

})();