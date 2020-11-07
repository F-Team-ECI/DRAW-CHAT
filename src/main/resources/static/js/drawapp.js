var drawapp = (function () {
    var userR;
    var sesssiontoken = null;



    var setUser = function (user) {
        console.log(user);
        userR = user;
        console.log("SETTING USER");
        $("#user").text(user.nombre + " " + user.apellido);
        chatSub.init();
        groupSub.init();
    }

    var addContactSuccess = function (data, status, xhr) {
        console.log(data)
        console.log(status)
    }

    var updateUser = function () {
        register.me(setUser);
    }

    

    updateUser();

    var sendUpdateSettings = function (data) {
        console.log(data);
        var request = $.ajax({
            url: '/users',
            type: "PUT",
            data: JSON.stringify(data),
            contentType: 'application/json;charset=UTF-8',
            success: function (data, status, xhr) {
                console.log(status);
                $("#ajustesSuccess").css({ display: "block" });
                $("#ajustesFail").css({ display: "none" });
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
                $("#ajustesFail").css({ display: "block" });
                $("#ajustesSuccess").css({ display: "none" });
            }
        });
        return request;
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
                    console.log(xhr.status);
                    $("#contectSuccess").css({ display: "block" });
                    $("#contactIncorrect").css({ display: "none" });
                },
                error: function (jqXhr, textStatus, errorMessage) {
                    console.log('Error' + errorMessage);
                    $("#contactIncorrect").css({ display: "block" });
                    $("#contectSuccess").css({ display: "none" });
                }
            });
        },
        setContacts: function (callback) {
            console.log(userR)
            $.ajax({
                url: '/users/' + userR.telefono + '/contacts',
                type: "GET",
                success: function (data, status, xhr) {
                    callback(data, status, xhr);
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

        },

        getPhone: function(){
            return userR.telefono;
        }, 

        getUser: function(){
            return userR;
        }
    }

})();