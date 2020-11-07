groupSub = (function () {

    var addGroupToView = function(group){
        var lema  = group.lema;
        var element = "<div class='groupInstance'>"
        + "<i>" + group.nombre +"</i>"
        + "<i> </i>"
        + "<i> </i>"
        + "<span>" + group.lema + "</span>"
        +"<i style='display: none;'>" + group.id + "</i>"
        + "</div>"
        $("#groupsContainer").append(element).hide().show(200);
    }

    var groupsRequest = function(){
        var req = $.ajax({
            url: '/groups',
            type: "GET",
            success: function (data, status, xhr) {
                console.log('status: ' + status + ', data: ' + data);
                console.log(data);
                data.map(function(obj){
                    addGroupToView(obj);
                });
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log('Error' + errorMessage);
            }
        });
        return req;
    }

    return {

        init: function () {
            groupsRequest().then(groupCreator.init);
            
        },
        addGroup : function(group){
            addGroupToView(group);
        }

    }

})();