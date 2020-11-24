var display;
var dropdown;
$(document).ready(function () {


    // Get the modal
    modal = $("#myModal:hidden");

    // Get the button that opens the modal
    var btn = $("#myBtn:hidden");

    // Get the <span> element that closes the modal
    var span = document.getElementById("closeConfig");

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
        if (event.target.id === "myModal") {
            modal.css({ display: "none" });
        }

        if (event.target.id === "contactsModal") {
            contacts.closeAdd();
        }

        if (event.target.id === "allContacts") {
            contacts.closeSeeContacts();
        }

        if (event.target.id === "addChat") {
            chatCreator.closeModal();
        }

        if (event.target.id === "addGroup") {
            groupCreator.closeModal();
        }

        if (event.target.id === "addMemberModal") {
            groupHanlder.closeAddModal();
        }
    }
    //  DROPDOWN
    dropdown =  function() {
        document.getElementById("myDropdown").classList.toggle("show");
    }

});