var display;
var modal;
var myFunction;
$(document).ready(function () {

    var setUser = function(user){
        console.log(user);
        $("#user").text(user.nombre + " "+ user.apellido);
    }
    
    var user = register.me(setUser);

    // Get the modal
    modal = $("#myModal:hidden");

    // Get the button that opens the modal
    var btn = $("#myBtn:hidden");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal 
    display = function (element) {
        console.log(modal)
        modal.css({ display: "block" });
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.css({ display: "none" });
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (!event.target.matches('.dropbtn')) {
            var dropdowns = document.getElementsByClassName("dropdown-content");
            var i;
            for (i = 0; i < dropdowns.length; i++) {
                var openDropdown = dropdowns[i];
                if (openDropdown.classList.contains('show')) {
                    openDropdown.classList.remove('show');
                }
            }
        }
        console.log(event.target.id)
        if (event.target.id === "myModal") {
            modal.css({ display: "none" });
        }
    }






    //  DROPDOWN
    myFunction =  function() {
        document.getElementById("myDropdown").classList.toggle("show");
    }

});