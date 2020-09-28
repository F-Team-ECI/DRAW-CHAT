register = (function(){

    var handleResponse= function(response, status){
        console.log(response);
        console.log(status);
    }

    return {

        push: function(){
            var name = $('#name').val();
            var lastName = $('#lastName').val();
            var password  = $('#password').val();
            var phone  = $('#phone').val();

            user = {
                "telefono": Number(phone),
                "nombre": name,
                "apellido": lastName,
                "contraseña": password
            }
            console.log(user);
            $.ajax({
                url: '/users',
                    type: "POST",
                    dataType: 'json',
                    data: JSON.stringify(user),
                    contentType: 'application/json;charset=UTF-8',
                    success: function (data, status, xhr) {
                        console.log('status: ' + status + ', data: ' + data);
                    },
                    error: function (jqXhr, textStatus, errorMessage) {
                        console.log('Error' + errorMessage);
                    }
            });
        },

        me: function(callback){
            ans = {};
            $.ajax({
                url: '/users/me',
                    type: "GET",
                    success: function (data, status, xhr) {
                        console.log('status: ' + status + ', data: ' + data);
                        callback(data);
                    },
                    error: function (jqXhr, textStatus, errorMessage) {
                        console.log('Error' + errorMessage);
                    }
            });
        }
    }
})();