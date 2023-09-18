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

    let storeid;
    let storeName;
    let cartPrice;
    let cartName;
    let totalPrice;

    $(".menu-box").click(function() {
        storeid = Number($(this).find(".store-id").val());
        storeName = $(this).find(".store-name").val();
        document.getElementsByClassName("menu-modal-img").src = $(this).find(".menu-img").val();
        cartPrice = Number($(this).find(".menu-price").val());
        cartName = $(this).find(".menu-name").val();
        let cartAmount = 1;
        totalPrice = cartAmount * cartPrice;

        $(".menu-modal-name").text(cartName);
        $(".price").text(Number(cartPrice).toLocaleString() + '원');
        $(".menu-total-price").text(Number(totalPrice).toLocaleString() + '원');
        $("#amount").val(Number(cartAmount));

        document.getElementById("menu-modal-img").src = $(this).find(".menu-img").val();
        document.getElementById("menu-modal").style.display="block";

        $(".minus-btn").off().click(function() {
            if (1 < Number($("#amount").val())) {
            	$("#amount").val(Number($("#amount").val()) - 1);
            }
            cartAmount = Number($("#amount").val());
            totalPrice = cartAmount * cartPrice;
            $(".menu-total-price").text(Number(totalPrice).toLocaleString() + '원');
        })

        $(".plus-btn").off().click(function() {
            $("#amount").val(Number($("#amount").val()) + 1);
            cartAmount = Number($("#amount").val());
            totalPrice = cartAmount * cartPrice;
            $(".menu-total-price").text(Number(totalPrice).toLocaleString() + '원');
        })
    })

    $(".add-cart").click(function(){
        const data = {
            storeid,
            storeName,
            cartName,
            cartPrice,
            cartAmount : $("#amount").val()
        }

        $.ajax({
            url: "/addCart",
            type: "post",
            data : data,
            traditional : true
        })
        .done(function(){
            console.log(data);
            document.getElementById("menu-modal").style.display="none";
        })
        .fail(function(){
            console.log("error");
        })

    })

    $(".close-btn").click(function(){
        document.getElementById("menu-modal").style.display="none";
    })

    $(".view-cart").click(function() {
        var link = '/cartList';
        location.href=link;
    })
});