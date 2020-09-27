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
                "telefono": phone,
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
                        $('p').append('status: ' + status + ', data: ' + data);
                    },
                    error: function (jqXhr, textStatus, errorMessage) {
                            $('p').append('Error' + errorMessage);
                    }
            });
        }
    }
})();