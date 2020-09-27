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
            $.post( "/users",user , function(data, status){
                handleResponse(data, status);
            });


        }

    }
})();
