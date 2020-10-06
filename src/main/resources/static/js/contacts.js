contacts = (function(){



    return {
        addContact: function(){
            modalContacts.css({ display: "block" });
        },

        closeAdd: function(){
            modalContacts.css({ display: "none" });
        },

        seeContacts: function(){
            seeAll.css({ display: "block" });
            $("#contactsLoad").css({ display: "block" })
            $("#contactList").empty();
            drawapp.setContacts();
        },

        closeSeeContacts(){
            seeAll.css({ display: "none" });
        }
    }
})();