var drawapp = (function () {
    var userR;
    var setUser = function (user) {
        console.log(user);
        userR = user;
        $("#user").text(user.nombre + " " + user.apellido);
    }

    var addContactSuccess = function(data, status, xhr){
        console.log(data)
        console.log(status)
    }

    register.me(setUser);
    console.log("HHHH")
    return {

        saveContact: function () {
            var a = $("#newPhone").val();
            var number = Number(a);
            console.log(number);
            $.ajax({
                url: '/contacts',
                type: "POST",
                data: "userA="+userR.telefono+"&userB="+number,
                contentType: 'application/x-www-form-urlencoded; charset=utf-8',
                success: function (data, status, xhr) {
                    setUser(data, status, xhr);
                },
                error: function (jqXhr, textStatus, errorMessage) {
                    console.log('Error' + errorMessage);
                }
            });
        },
        setContacts: function(){
            
        }
    }

})();