var drawapp = (function(){
 
    var setUser = function(user){
        console.log(user);
        $("#user").text(user.nombre + " "+ user.apellido);
    }
    
    var user = register.me(setUser);

    return {

        
    }

})();