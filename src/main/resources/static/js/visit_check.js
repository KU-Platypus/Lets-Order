$(document).ready(function() {
    $(function () {
        var token = $("meta[name='_csrf']").attr('content');
        var header = $("meta[name='_csrf_header']").attr('content');
        if(token && header) {
            $(document).ajaxSend(function(event, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        }
    });

    check_visit();

    function check_visit(){
        let html = "";
    	$.ajax({
    		url: "/checkVisit",
    		type: "get",
    	})
    	.done(function(result){
    	    if(result == "1"){
    	        document.getElementById("checkpoint").src = "/img/icon/visit.png";
    	    }
    	    else if(result == "0"){
    	        document.getElementById("checkpoint").src = "/img/icon/first.png";
    	    }
    	    else if(result == "2"){
                document.getElementById("checkpoint").src = "";
            }
    	})
    	.fail(function(){
    		console.log("error");
    	})
    }
});