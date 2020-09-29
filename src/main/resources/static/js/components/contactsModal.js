var contactsOpen;
var modalContacts;

$(document).ready(function () {

    modalContacts = $("#contactsModal:hidden");

    var btn = $("#myBtn:hidden");
    var span = document.getElementById("closeContacts");
    contactsOpen = function (element) {
        console.log(modal)
        modalContacts.css({ display: "block" });
    }
    span.onclick = function () {
        modalContacts.css({ display: "none" });
    }
});