var modalContacts;
var seeAll;

$(document).ready(function () {

    modalContacts = $("#contactsModal:hidden");

    var btn = $("#myBtn:hidden");
    var span = document.getElementById("closeContacts");

    span.onclick = function () {
        contacts.closeAdd();
    }



    //ADDING SEE ALL CONTACTS MODAL

    seeAll = $("#allContacts:hidden");

    var close = document.getElementById("closeSeeContacts");
    close.onclick = function () {
        contacts.closeSeeContacts();
    }

});