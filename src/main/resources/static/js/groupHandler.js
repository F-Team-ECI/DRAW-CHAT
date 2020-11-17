var addMemberModal;
var groupHanlder = (function(){


    $(document).ready(function () {

        addMemberModal = $("#addMemberModal:hidden");
        var span = document.getElementById("closeAddMember");

        span.onclick = function () {
            groupHanlder.closeAddModal();
        }
    });

    
    return {

        closeAddModal: function(){
            addMemberModal.css({ display: "none" });
            $("#addMemberMain").empty();
        },

        openAddModal: function(){
            addMemberModal.css({ display: "flex" });  
            $("#addMemberLoad").css({ display: "block" })

        }

    }

})();