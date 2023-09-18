$(document).ready(function(){
    var code = $('.category-code').val();
    console.log(code)

    if(code == '100'){
        $(".tab1").css({
            "border-bottom" : "10px solid #313131"
        });
    }

    else if(code == '200'){
        $(".tab2").css({
            "border-bottom" : "10px solid #313131"
        });
    }
    else if(code == '300'){
        $(".tab3").css({
            "border-bottom" : "10px solid #313131"
        });
    }
    else if(code == '400'){
        $(".tab4").css({
            "border-bottom" : "10px solid #313131"
        });
    }
    else if(code == '500'){
        $(".tab5").css({
            "border-bottom" : "10px solid #313131"
        });
    }
    else {
        console.log("error");
    }
});